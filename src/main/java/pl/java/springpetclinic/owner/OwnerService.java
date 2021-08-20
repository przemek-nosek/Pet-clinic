package pl.java.springpetclinic.owner;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.java.springpetclinic.dto.OwnerFirstAndLastNameOnly;
import pl.java.springpetclinic.exception.*;
import pl.java.springpetclinic.pet.Pet;
import pl.java.springpetclinic.pet.PetRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;

    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with given id: %d not found", id)));
    }

    public Owner addOwner(Owner owner) {
        String phoneNumber = owner.getPhoneNumber();

        isPhoneValid(phoneNumber);

        if (existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberAlreadyExistsException(String.format("Phone number: %s already exists", phoneNumber));
        }

        return ownerRepository.save(owner);
    }

    private void isPhoneValid(String phoneNumber) {
        if (!PhoneNumberValidator.validatePhoneNumber(phoneNumber)) {
            throw new PhoneNumberIllegalFormatException(String.format("Given phone number %s is in wrong format", phoneNumber));
        }
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return ownerRepository.existsByPhoneNumber(phoneNumber);
    }

    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner deleteOwnerById(Long id) {
        Owner owner = ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with given id: %d not found", id)));

        ownerRepository.deleteById(id);
        return owner;
    }

    @Transactional
    public void updateFirstnameAndLastname(Long id, OwnerFirstAndLastNameOnly toUpdate) {
        ownerExists(id);
        String firstName = toUpdate.getFirstName();
        String lastName = toUpdate.getLastName();
        ownerRepository.updateFirstnameAndLastname(id, firstName, lastName);
    }

    @Transactional
    public void phoneNumberOwnerUpdate(Long id, String phoneNumber) {
        ownerExists(id);

        isPhoneValid(phoneNumber);

        ownerRepository.updatePhoneNumber(id, phoneNumber);
    }

    private void ownerExists(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new OwnerNotFoundException(String.format("Owner with given id: %d not found", id));
        }
    }

    public Owner addPetToOwner(Long id, Pet pet) {
        Owner foundOwner = findOwnerById(id);
        foundOwner.addPet(pet);

        return ownerRepository.save(foundOwner);
    }

    public Owner removePetFromOwner(Long id, Long petId) {
        Pet pet = petRepository.findById(petId).orElseThrow(() -> new PetNotFoundException(String.format("Pet with given id: %d not found", petId)));

        Owner foundOwner = findOwnerById(id);

        if (!foundOwner.getPets().contains(pet)) {
            throw new PetBelongsToSomeoneElseException("Pet belongs to someone else");
        }

        foundOwner.removePet(pet);

        return ownerRepository.save(foundOwner);
    }

    public List<Owner> findAllOwners(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

        Page<Owner> pagedResult = ownerRepository.findAll(pageable);

        return pagedResult.getContent();
    }
}

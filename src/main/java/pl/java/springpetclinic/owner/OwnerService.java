package pl.java.springpetclinic.owner;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.java.springpetclinic.exception.OwnerNotFoundException;
import pl.java.springpetclinic.exception.PhoneNumberAlreadyExistsException;

import java.util.List;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new OwnerNotFoundException(String.format("Owner with given id: %d not found", id)));
    }

    public Owner addOwner(Owner owner) {
        String phoneNumber = owner.getPhoneNumber();

        if (existsByPhoneNumber(phoneNumber)) {
            throw new PhoneNumberAlreadyExistsException(String.format("Phone number: %s already exists",phoneNumber));
        }

        return ownerRepository.save(owner);
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
}

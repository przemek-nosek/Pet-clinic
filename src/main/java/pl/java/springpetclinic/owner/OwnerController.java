package pl.java.springpetclinic.owner;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.java.springpetclinic.dto.OwnerDto;
import pl.java.springpetclinic.dto.OwnerFirstAndLastNameOnly;
import pl.java.springpetclinic.mapper.OwnerMapper;
import pl.java.springpetclinic.pet.Pet;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<OwnerDto>> findAllOwners(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy) {

        List<Owner> allOwners = ownerService.findAllOwners(page, size, sortBy);

        List<OwnerDto> ownerDtos = allOwners.stream()
                .map(OwnerMapper.INSTANCE::ownerToOwnerDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ownerDtos, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<OwnerDto>> findAllOwnersAll() {
        List<Owner> owners = ownerService.findAllOwners();

        List<OwnerDto> ownerDtos =
                owners.stream()
                        .map(OwnerMapper.INSTANCE::ownerToOwnerDto)
                        .collect(Collectors.toList());


        return new ResponseEntity<>(ownerDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> findOwnerById(@PathVariable("id") Long id) {
        Owner owner = ownerService.findOwnerById(id);

        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Owner> addOwner(@Valid @RequestBody Owner owner) {
        Owner addedOwner = ownerService.addOwner(owner);

        return new ResponseEntity<>(addedOwner, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editOwner(@PathVariable("id") Long id, @Valid @RequestBody Owner ownerDetails) {
        Owner owner = ownerService.findOwnerById(id);

        owner.setFirstName(ownerDetails.getFirstName());
        owner.setLastName(ownerDetails.getLastName());

        String phoneNumber = ownerDetails.getPhoneNumber();
        if (!owner.getPhoneNumber().equals(phoneNumber) && PhoneNumberValidator.validatePhoneNumber(phoneNumber)) {
            owner.setPhoneNumber(phoneNumber);
        }

        owner.removePets();
        owner.setPets(ownerDetails.getPets());

        ownerService.save(owner);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/pets/add")
    public ResponseEntity<Owner> addPetToOwner(@PathVariable("id") Long id, @Valid @RequestBody Pet pet) {
        Owner owner = ownerService.addPetToOwner(id, pet);

        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @PatchMapping("/{id}/pets/{petId}")
    public ResponseEntity<Owner> removePetFromOwner(@PathVariable("id") Long id, @PathVariable Long petId) {
        Owner owner = ownerService.removePetFromOwner(id, petId);

        return new ResponseEntity<>(owner, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> FirstAndLastNameOwnerUpdate(@PathVariable("id") Long id, @RequestBody OwnerFirstAndLastNameOnly toUpdate) {
        ownerService.updateFirstnameAndLastname(id, toUpdate);

        return ResponseEntity.ok("First name and last name updated.");
    }

    @PatchMapping("/{id}/{phoneNumber}")
    public ResponseEntity<?> phoneNumberOwnerUpdate(@PathVariable("id") Long id, @PathVariable("phoneNumber") String phoneNumber) {
        ownerService.phoneNumberOwnerUpdate(id, phoneNumber);

        return ResponseEntity.ok("Phone number updated.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Owner> deleteOwner(@PathVariable("id") Long id) {
        Owner owner = ownerService.deleteOwnerById(id);

        return new ResponseEntity<>(owner, HttpStatus.OK);
    }
}

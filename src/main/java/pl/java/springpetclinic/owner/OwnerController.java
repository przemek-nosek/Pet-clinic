package pl.java.springpetclinic.owner;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.java.springpetclinic.dto.OwnerFirstAndLastNameOnly;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @GetMapping
    public ResponseEntity<List<Owner>> findAllOwners() {
        List<Owner> owners = ownerService.findAllOwners();

        return new ResponseEntity<>(owners, HttpStatus.OK);
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

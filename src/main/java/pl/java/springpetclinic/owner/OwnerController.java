package pl.java.springpetclinic.owner;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}

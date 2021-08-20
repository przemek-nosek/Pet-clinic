package pl.java.springpetclinic.owner;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.java.springpetclinic.pet.Pet;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "owners")
@Getter
@Setter
@NoArgsConstructor
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 2, max = 45)
    private String firstName;

    @Size(min = 2, max = 45)
    private String lastName;
    private String phoneNumber; // TO VALIDATE

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    @NotEmpty(message = "Owner should have at least 1 pet.")
    private Set<Pet> pets = new HashSet<>();

    public Owner(String firstName, String lastName, String phoneNumber, Set<Pet> pets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.pets = pets;
    }

    public Pet addPet(Pet pet) {
        this.pets.add(pet);
        return pet;
    }

    public Pet removePet(Pet pet) {
        this.pets.remove(pet);
        return pet;
    }
}

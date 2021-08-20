package pl.java.springpetclinic;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.java.springpetclinic.owner.Owner;
import pl.java.springpetclinic.owner.OwnerRepository;
import pl.java.springpetclinic.pet.Pet;

import java.util.Random;
import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class SpringPetClinicApplication implements CommandLineRunner {

    private final OwnerRepository ownerRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringPetClinicApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        for (int i = 0; i < 10; i++) {
//            Pet pet = new Pet(generateString());
//            Owner owner = new Owner(generateString(),generateString(), generateString(), Set.of(pet));
//            ownerRepository.save(owner);
//        }
    }

    private String generateString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

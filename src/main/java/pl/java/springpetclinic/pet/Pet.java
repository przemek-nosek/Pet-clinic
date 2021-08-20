package pl.java.springpetclinic.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pets")
@Getter
@Setter
@NoArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotNull
    @NotBlank
    private String type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "Birth date can't be in future.")
    @Column(updatable = false)
    private LocalDate birthDate;

    public Pet(String name) {
        this.name = name;
    }

    public Pet(String name, String type, LocalDate birthDate) {
        this.name = name;
        this.type = type;
        this.birthDate = birthDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pet pet)) return false;
        return Objects.equals(getId(), pet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}

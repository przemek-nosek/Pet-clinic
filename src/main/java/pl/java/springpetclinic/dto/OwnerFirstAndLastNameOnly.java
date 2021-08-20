package pl.java.springpetclinic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OwnerFirstAndLastNameOnly {
    @Size(min = 2, max = 45)
    private String firstName;
    @Size(min = 2, max = 45)
    private String lastName;
}

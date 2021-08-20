package pl.java.springpetclinic.dto;

import javax.validation.constraints.NotNull;

public class OwnerPhoneNumberOnly {
    @NotNull
    private String phoneNumber;
}

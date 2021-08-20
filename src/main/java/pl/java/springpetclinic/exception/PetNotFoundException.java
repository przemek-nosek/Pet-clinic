package pl.java.springpetclinic.exception;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException() {
    }

    public PetNotFoundException(String message) {
        super(message);
    }
}

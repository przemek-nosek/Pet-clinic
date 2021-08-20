package pl.java.springpetclinic.exception;

public class OwnerNotFoundException extends RuntimeException {
    public OwnerNotFoundException() {
        super();
    }

    public OwnerNotFoundException(String message) {
        super(message);
    }
}

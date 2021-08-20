package pl.java.springpetclinic.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException {
    public PhoneNumberAlreadyExistsException() {
    }

    public PhoneNumberAlreadyExistsException(String message) {
        super(message);
    }
}

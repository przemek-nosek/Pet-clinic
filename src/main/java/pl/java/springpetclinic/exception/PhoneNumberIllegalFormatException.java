package pl.java.springpetclinic.exception;

public class PhoneNumberIllegalFormatException extends RuntimeException {
    public PhoneNumberIllegalFormatException() {
    }

    public PhoneNumberIllegalFormatException(String message) {
        super(message);
    }
}

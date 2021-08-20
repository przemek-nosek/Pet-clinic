package pl.java.springpetclinic.exception;

public class PetBelongsToSomeoneElseException extends RuntimeException{
    public PetBelongsToSomeoneElseException() {
        super();
    }

    public PetBelongsToSomeoneElseException(String message) {
        super(message);
    }
}

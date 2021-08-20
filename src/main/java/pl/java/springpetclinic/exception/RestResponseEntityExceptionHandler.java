package pl.java.springpetclinic.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OwnerNotFoundException.class})
    public <T extends RuntimeException> ResponseEntity<ErrorMessage> handleNotFoundException(T ex, WebRequest request) {
        return getErrorMessageResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler({OwnerNotFoundException.class})
    public ResponseEntity<ErrorMessage> handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex, WebRequest request) {
        return getErrorMessageResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), Collections.emptyList());
    }

    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), httpStatus, message, errors);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}

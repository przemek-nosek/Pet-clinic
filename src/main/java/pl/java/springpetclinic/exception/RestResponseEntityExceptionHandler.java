package pl.java.springpetclinic.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({OwnerNotFoundException.class})
    public <T extends RuntimeException> ResponseEntity<ErrorMessage> handleNotFoundException(T ex, WebRequest request) {
        return getErrorMessageResponseEntity(HttpStatus.NOT_FOUND, ex.getMessage(), Collections.emptyList());
    }

    @ExceptionHandler({PhoneNumberAlreadyExistsException.class})
    public ResponseEntity<ErrorMessage> handlePhoneNumberAlreadyExistsException(PhoneNumberAlreadyExistsException ex, WebRequest request) {
        return getErrorMessageResponseEntity(HttpStatus.CONFLICT, ex.getMessage(), Collections.emptyList());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getField() + " " + fieldError.getDefaultMessage());
        }

        ResponseEntity<ErrorMessage> errorMessage = getErrorMessageResponseEntity(status, ex.getMessage(), errors);

        return new ResponseEntity<>(errorMessage, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ResponseEntity<ErrorMessage> errorMessage = getErrorMessageResponseEntity(status, ex.getMessage(), Collections.emptyList());

        return new ResponseEntity<>(errorMessage, status);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex, WebRequest request) {
        return getErrorMessageResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), Collections.emptyList());
    }

    private ResponseEntity<ErrorMessage> getErrorMessageResponseEntity(HttpStatus httpStatus, String message, List<String> errors) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), httpStatus, message, errors);

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}

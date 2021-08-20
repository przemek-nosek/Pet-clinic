package pl.java.springpetclinic.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ErrorMessage {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime timeStamp;
    private HttpStatus httpStatus;
    private String message;
    private List<String> errors = new ArrayList<>();

    public ErrorMessage(LocalDateTime timeStamp, HttpStatus httpStatus, String message) {
        this.timeStamp = timeStamp;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ErrorMessage(LocalDateTime timeStamp, HttpStatus httpStatus, String message, List<String> errors) {
        this.timeStamp = timeStamp;
        this.httpStatus = httpStatus;
        this.message = message;
        this.errors = errors;
    }
}

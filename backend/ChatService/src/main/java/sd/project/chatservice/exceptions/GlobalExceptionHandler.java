package sd.project.chatservice.exceptions;

import org.postgresql.util.PSQLException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static sd.project.chatservice.utils.ErrorMessages.VALIDATION_EXCEPTION_MESSAGE;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException userNotFoundException) {
        ExceptionResponse apiExceptionResponse = new ExceptionResponse(
                userNotFoundException.getMessage(),
                NOT_FOUND,
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(apiExceptionResponse, NOT_FOUND);
    }

    @ExceptionHandler(CouldNotSendMessageException.class)
    public ResponseEntity<Object> handleCouldNotSendMessageException(CouldNotSendMessageException couldNotSendMessageException) {
        ExceptionResponse apiExceptionResponse = new ExceptionResponse(
                couldNotSendMessageException.getMessage(),
                NOT_FOUND,
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(apiExceptionResponse, NOT_FOUND);
    }

    @ExceptionHandler(PSQLException.class)
    public ResponseEntity<Object> handleConstraintViolationException(PSQLException psqlException) {
        ExceptionResponse apiExceptionResponse = new ExceptionResponse(
                psqlException.getMessage(),
                BAD_REQUEST,
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(apiExceptionResponse, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = methodArgumentNotValidException.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, error -> error.getDefaultMessage()));
        ExceptionResponse apiExceptionResponse = new ExceptionResponse(
                VALIDATION_EXCEPTION_MESSAGE,
                BAD_REQUEST,
                LocalDateTime.now(),
                errors
        );
        return new ResponseEntity<>(apiExceptionResponse, BAD_REQUEST);
    }
}

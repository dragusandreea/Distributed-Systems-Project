package sd.project.devicemanagementservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public record ExceptionResponse(String message, HttpStatus httpStatus, @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime, Map<String, String> errors) {
}

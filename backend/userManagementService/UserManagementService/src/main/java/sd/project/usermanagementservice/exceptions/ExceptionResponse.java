package sd.project.usermanagementservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

public record ExceptionResponse(String message, HttpStatus httpStatus, @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime localDateTime, Map<String, String> errors) {
}

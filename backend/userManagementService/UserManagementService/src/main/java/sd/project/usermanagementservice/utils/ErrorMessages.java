package sd.project.usermanagementservice.utils;

public class ErrorMessages {
    public static final String USER_NOT_FOUND_BY_ID_MESSAGE = "User not found by id = %s";
    public static final String USER_NOT_FOUND_BY_USERNAME_MESSAGE = "User not found by username = %s";
    public static final String USER_NOT_FOUND_BY_NAME_MESSAGE = "User not found by name = %s";
    public static final String INVALID_EMAIL_FORMAT_ERROR_MESSAGE = "Username  must have a valid email format";
    public static final String BLANK_USERNAME_ERROR_MESSAGE = "Username cannot be blank";
    public static final String BLANK_PASSWORD_ERROR_MESSAGE = "Password cannot be blank";
    public static final String BLANK_NAME_MESSAGE = "Name cannot be blank";
    public static final String NULL_PASSWORD_ERROR_MESSAGE = "Null password";
    public static final String NULL_USER_TYPE_MESSAGE  = "Null user type";
    public static final String VALIDATION_EXCEPTION_MESSAGE = "Validation exception";
    private ErrorMessages() {

    }
}

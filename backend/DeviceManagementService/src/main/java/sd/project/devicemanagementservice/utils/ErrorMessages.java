package sd.project.devicemanagementservice.utils;

public class ErrorMessages {
    public static final String DEVICE_NOT_FOUND_BY_ID_MESSAGE = "Device not found by id = %s";
    public static final String DEVICE_NOT_FOUND_BY_ADDRESS_MESSAGE = "Device not found by address = %s";
    public static final String DEVICE_NOT_FOUND_BY_DESCRIPTION_MESSAGE = "Device not found by description = %s";
    public static final String DEVICE_NOT_FOUND_BY_HOURLY_LIMIT_MESSAGE = "Device not found by hourly limit = %d";
    public static final String DEVICE_NOT_FOUND_BY_HOURLY_LIMIT_LESS_THAN_MESSAGE = "Device not found by hourly limit less than = %d";
    public static final String DEVICE_NOT_FOUND_BY_HOURLY_LIMIT_GREATER_THAN_MESSAGE = "Device not found by hourly limit greater than = %d";
    public static final String DEVICE_NOT_FOUND_BY_OWNER_ID_MESSAGE = "Device not found by owner id = %s";
    public static final String BLANK_DESCRIPTION_ERROR_MESSAGE = "Description cannot be blank";
    public static final String BLANK_ADDRESS_ERROR_MESSAGE = "Address cannot be blank";
    public static final String NULL_HOURLY_LIMIT_ERROR_MESSAGE = "Hourly Energy Consumption Limit cannot be null";
    public static final String NULL_OWNER_ID_ERROR_MESSAGE = "Owner id cannot be null";
    public static final String VALIDATION_EXCEPTION_MESSAGE = "Validation exception";
    private ErrorMessages() {

    }
}

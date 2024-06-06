package sd.project.chatservice.exceptions;

public class CouldNotSendMessageException extends RuntimeException {
    public CouldNotSendMessageException(String message) {
        super(message);
    }
}

package salt.consultanttracker.api.exceptions;

public class UnexpectedResponseException extends RuntimeException {
    public UnexpectedResponseException(String message) {
        super(message);
    }
}

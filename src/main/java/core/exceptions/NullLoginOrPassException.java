package core.exceptions;

public class NullLoginOrPassException extends Exception {
    public NullLoginOrPassException() {
    }

    public NullLoginOrPassException(String message) {
        super(message);
    }

    public NullLoginOrPassException(String message, Throwable cause) {
        super(message, cause);
    }
}

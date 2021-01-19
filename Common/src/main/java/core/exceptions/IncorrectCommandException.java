package core.exceptions;

public class IncorrectCommandException extends Exception {
    public IncorrectCommandException() {
    }

    public IncorrectCommandException(String message) {
        super(message);
    }

    public IncorrectCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}

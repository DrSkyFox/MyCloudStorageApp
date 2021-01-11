package core.exceptions;

public class SomeThingWrongException extends Exception {
    public SomeThingWrongException() {
    }

    public SomeThingWrongException(String message) {
        super(message);
    }

    public SomeThingWrongException(String message, Throwable cause) {
        super(message, cause);
    }
}

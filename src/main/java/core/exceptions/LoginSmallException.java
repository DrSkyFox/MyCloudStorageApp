package core.exceptions;

public class LoginSmallException extends Exception{

    public LoginSmallException() {
    }

    public LoginSmallException(String message) {
        super(message);
    }

    public LoginSmallException(String message, Throwable cause) {
        super(message, cause);
    }
}

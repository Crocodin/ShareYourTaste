package ro.iss.backend.exception;

public class FailedAuthentication extends RuntimeException {
    public FailedAuthentication(String message) {
        super(message);
    }
}

package wolox.training.exceptions;

public class UserIdMismatchException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UserIdMismatchException(final String message) {
        super(message);
    }
	
    public UserIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

package wolox.training.exceptions;

public class BookIdMismatchException extends RuntimeException {

	private static final long serialVersionUID = 2L;

	public BookIdMismatchException(final String message) {
        super(message);
    }
	
    public BookIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

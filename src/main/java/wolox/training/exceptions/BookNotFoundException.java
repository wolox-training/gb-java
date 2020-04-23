package wolox.training.exceptions;

public class BookNotFoundException  extends RuntimeException {

	private static final long serialVersionUID = 1L;

    public BookNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}

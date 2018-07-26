package springboot.exception;

public class TestException extends Exception {
	
	private static final long serialVersionUID = -4704711433682589936L;

    /**
     * Constructs a {@code TestException} with no specified detail
     * message.
     */
    public TestException() {}

    /**
     * Constructs a {@code TestException} with the specified detail
     * message.
     *
     * @param message the detail message
     */
    public TestException(String message) {
        super(message);
    }
    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(Throwable cause) {
        super(cause);
    }
}
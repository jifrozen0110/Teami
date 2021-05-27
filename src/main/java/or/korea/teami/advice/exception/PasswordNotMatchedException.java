package or.korea.teami.advice.exception;

public class PasswordNotMatchedException extends RuntimeException {
	public PasswordNotMatchedException() {
		super();
	}

	public PasswordNotMatchedException(String message) {
		super(message);
	}

	public PasswordNotMatchedException(String message, Throwable cause) {
		super(message, cause);
	}
}

package or.korea.teami.advice.exception;

public class SigninFailedException extends RuntimeException {
	public SigninFailedException() {
		super();
	}

	public SigninFailedException(String message) {
		super(message);
	}

	public SigninFailedException(String message, Throwable cause) {
		super(message, cause);
	}
}

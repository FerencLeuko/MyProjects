package narrative_analyser.starter;

public class ExitException extends Exception {

	private static final long serialVersionUID = 1L;

	public ExitException() {
		super();
	}

	ExitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	ExitException(String message, Throwable cause) {
		super(message, cause);
	}

	ExitException(String message) {
		super(message);
	}

	ExitException(Throwable cause) {
		super(cause);
	}

}

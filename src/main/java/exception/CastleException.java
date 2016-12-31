package exception;

public class CastleException extends Exception {

	public CastleException(String message) {
		super(message);
	}

	public CastleException(CastleException exception) {
		super(exception);
	}

	public CastleException(Exception e) {
		super(e);
	}

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

}

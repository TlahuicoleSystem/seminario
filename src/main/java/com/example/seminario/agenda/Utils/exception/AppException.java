package  com.example.seminario.agenda.Utils.exception;

public class AppException extends Exception {

	protected boolean logged;

	public AppException() {
		super();
	}

	public AppException(String msg) {
		super(msg);
	}

	public AppException(String message, Throwable cause, boolean logged) {
		super(message, cause);
		this.logged = logged;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}
}
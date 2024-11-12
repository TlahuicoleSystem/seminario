package  com.example.seminario.agenda.Utils;


public class ResponseBean<T>{
    private boolean success;
	private String message;
	private T data;
	private int total;
	private String[] invalidParams;
	private Exception exception;

	// success true
	public ResponseBean() {
		this.success = true;
	}

	public ResponseBean(String message) {
		this.success = true;
		this.message = message;
	}

	public ResponseBean(T data) {
		this.success = true;
		this.data = data;
	}

	public ResponseBean(T data, int total) {
		this.success = true;
		this.data = data;
		this.total = total;
	}

	public ResponseBean(String message, T data) {
		this.success = true;
		this.message = message;
		this.data = data;
	}

	public ResponseBean(String message, T data, int total) {
		this.success = true;
		this.message = message;
		this.data = data;
		this.total = total;
	}

	// success false
	public ResponseBean(boolean success) {
		this.success = success;
	}

	public ResponseBean(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ResponseBean(boolean success, String message, T data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public ResponseBean(boolean success, String message, Exception exception) {
		this.success = success;
		this.message = message;
		this.exception = exception;
	}

	public ResponseBean(boolean success, String message, String[] invalidParams) {
		this.success = success;
		this.message = message;
		this.invalidParams = invalidParams;
	}



	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public String[] getInvalidParams() {
		return invalidParams;
	}

	public void setInvalidParams(String[] invalidParams) {
		this.invalidParams = invalidParams;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	} 
}

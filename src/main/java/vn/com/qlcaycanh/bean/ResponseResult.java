package vn.com.qlcaycanh.bean;

public class ResponseResult<T> {
	private int status;
	private String message;
	private T result;
	
	public ResponseResult(int status, String message, T result) {
		this.status = status;
		this.result = result;
		this.message = message;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}

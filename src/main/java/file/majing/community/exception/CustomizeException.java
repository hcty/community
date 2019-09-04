package file.majing.community.exception;

/**
 * Created by hechuan on 2019/9/4;
 */
public class CustomizeException extends RuntimeException{
	private String message;

	public CustomizeException(ICustomizeErrorCode errorCode) {
		this.message = errorCode.getMessage();
	}

	@Override public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

package file.majing.community.exception;

/**
 * 异常信息
 * Created by hechuan on 2019/9/4;
 */
public class CustomizeException extends RuntimeException {
	private String message;
	private Integer code;

	public CustomizeException(ICustomizeErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

	@Override public String getMessage() {
		return message;
	}

	public Integer getCode() {
		return code;
	}
}

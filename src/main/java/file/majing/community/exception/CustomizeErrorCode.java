package file.majing.community.exception;

/**
 * 异常提醒的错误信息枚举
 * Created by hechuan on 2019/9/4;
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
	/**
	 * 查询不存在的异常提醒
	 * @Author: hechuan on 2019/9/4 21:56
	 * @param:
	 * @return:
	 */
	QUESTION_NOT_FOUND("你找的问题不在了，要不要换个试试^-^!");
	private String message;

	@Override public String getMessage() {
		return message;
	}

	CustomizeErrorCode(String message) {
		this.message = message;
	}
}

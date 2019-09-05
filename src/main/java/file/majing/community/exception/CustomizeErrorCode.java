package file.majing.community.exception;

/**
 * 异常提醒的错误信息枚举
 * Created by hechuan on 2019/9/4;
 */
public enum CustomizeErrorCode implements ICustomizeErrorCode {
	/**
	 * 查询不存在的异常提醒
	 *
	 * @Author: hechuan on 2019/9/4 21:56
	 * @param:
	 * @return:
	 */
	QUESTION_NOT_FOUND(2001, "你找的问题不在了，要不要换个试试^-^!"),
	TARGET_PARAM_NOT_FOUND(2002, "未选择任何问题或评论进行回复"),
	NO_LOGIN(2003, "未登录不能进行评论，请先登录"),
	SYS_ERROR(2004, "服务冒烟了，要不然你稍后再试试!!!"),
	TYPE_PARAM_WRONG(2005, "评论类型错误或不存在"),
	COMMENT_NOT_FOUND(2006, "你操作的评论错误或不存在")
	;
	private Integer code;
	private String message;

	@Override public String getMessage() {
		return message;
	}

	@Override public Integer getCode() {
		return code;
	}

	CustomizeErrorCode(Integer code, String message) {
		this.code = code;
		this.message = message;
	}
}

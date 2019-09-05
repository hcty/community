package file.majing.community.enums;

/**
 * Created by hechuan on 2019/9/5;
 */
public enum CommentTypeEnum {
	QUESTION(1), COMMENT(2);
	private Integer type;

	public static boolean isExist(Integer type) {
		for (CommentTypeEnum commentTypeEnum : CommentTypeEnum.values()) {
			if (commentTypeEnum.getType().intValue()==type.intValue()) {
				return true;
			}
		}
		return false;
	}

	public Integer getType() {
		return type;
	}

	CommentTypeEnum(Integer type) {
		this.type = type;
	}
}

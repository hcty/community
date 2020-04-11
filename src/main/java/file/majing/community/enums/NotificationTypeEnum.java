package file.majing.community.enums;

public enum NotificationTypeEnum {
	REPLY_QUESTION(1,"回复了问题"),
	REPLY_COMMENT(2,"回复了评论")
	;
	private int type;
	private String name;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	NotificationTypeEnum(int status, String name) {
		this.type = status;
		this.name = name;
	}
	public static String nameOfType(int type){
		for (NotificationTypeEnum typeEnum : NotificationTypeEnum.values()) {
			if(typeEnum.getType() == type){
				return typeEnum.getName();
			}
		}
		return "";
	}
}

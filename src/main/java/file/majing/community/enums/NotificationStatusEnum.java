package file.majing.community.enums;

public enum NotificationStatusEnum {
	UNREAD(0,"未读"),
	READ(1,"已未读")
	;
	private int status;
	private String name;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	NotificationStatusEnum(int status, String name) {
		this.status = status;
		this.name = name;
	}
}

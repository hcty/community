package file.majing.community.model;

public class User {
	private Integer id;
	private String name;
	private String accountId;
	private String token;
	private long gmtCreate;
	private long gmtModified;

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}



	public void setToken(String token) {
		this.token = token;
	}

	public void setGmtCreate(long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(long gmtModified) {
		this.gmtModified = gmtModified;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	public String getToken() {
		return token;
	}

	public long getGmtCreate() {
		return gmtCreate;
	}

	public long getGmtModified() {
		return gmtModified;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
}

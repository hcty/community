package file.majing.community.dto;

public class GItHubUser {
	/**
	 * GitHub用户名
	 */
	private String name;
	/**
	 * GitHub用户id
	 */
	private Long id;
	/**
	 * GitHub用户描述
	 */
	private String bio;

	public void setName(String name) {
		this.name = name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getName() {
		return name;
	}

	public Long getId() {
		return id;
	}

	public String getBio() {
		return bio;
	}

	@Override public String toString() {
		return "GItHubUser{" + "name='" + name + '\'' + ", id=" + id + ", bio='" + bio + '\'' + '}';
	}
}

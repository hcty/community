package file.majing.community.model;

public class Question {
	private Integer id;
	private String title;
	private String description;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer creator;
	private String tag;
	private Integer viewCount;
	private Integer vommentCount;
	private Integer likeCount;

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGmtCreate(Long gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public void setGmtModified(Long gmtModified) {
		this.gmtModified = gmtModified;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}

	public void setVommentCount(Integer vommentCount) {
		this.vommentCount = vommentCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Long getGmtCreate() {
		return gmtCreate;
	}

	public Long getGmtModified() {
		return gmtModified;
	}

	public Integer getCreator() {
		return creator;
	}

	public Integer getViewCount() {
		return viewCount;
	}

	public Integer getVommentCount() {
		return vommentCount;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

}

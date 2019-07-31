package file.majing.community.dto;

import lombok.Data;

@Data
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
	/**
	 * 用户头像
	 */
	private String avatarUrl;
}

package file.majing.community.dto;

import lombok.Data;

/**
 * 数据传输对象 --获取GitHub的accessToken
 */
@Data
public class AccessTokenDTO {
	/**
	 * 需要。您从GitHub收到的GitHub应用程序的客户端ID
	 */
	private String client_id;
	/**
	 * 需要。您从GitHub收到的GitHub应用程序的客户机密。
	 */
	private String client_secret;
	/**
	 * 您收到的代码作为对第1步的回复。
	 */
	private String code;
	/**
	 * 应用程序中的URL，用于在授权后发送用户。
	 */
	private String redirect_uri;
	/**
	 * 您在步骤1中提供的不可思议的随机字符串。
	 */
	private String state;
}

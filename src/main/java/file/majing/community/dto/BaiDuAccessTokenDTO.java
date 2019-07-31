package file.majing.community.dto;

import lombok.Data;

/**
 * 数据传输对象 --获取百度的accessToken
 */
@Data
public class BaiDuAccessTokenDTO {
	private String grant_type;
	private String code;
	private String client_id;
	private String client_secret;
	private String redirect_uri;

}

package file.majing.community.dto;

import file.majing.community.model.User;
import lombok.Data;

/**
 * Created by hechuan on 2020/4/11;
 */
@Data
public class NotificationDTO {
	private Long id;
	private Long getCreate;
	private Integer status;
	private Long notifier;
	private String notifierName;
	private String outerTitle;
	private Long outerid;
	private String typeName;
	private int type;
}

package file.majing.community.dto;

import file.majing.community.model.User;
import lombok.Data;

/**
 * 消息通知dto
 * Created by hechuan on 2020/4/11;
 */
@Data public class NotificationDTO {
	private Long id;//消息id
	private Long getCreate;//消息创建人id
	private Integer status;//消息状态
	private Long notifier;//评论人id
	private String notifierName;//评论人名称
	private String outerTitle;//消息标题
	private Long outerid;//问题id
	private String typeName;//消息类型名称
	private int type;//消息类型
}

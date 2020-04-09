package file.majing.community.dto;

import file.majing.community.model.User;
import lombok.Data;

/**
 * Created by hechuan on 2019/9/6;
 */
@Data
public class CommentDTO {
	private Long id;
	private Long parentId;
	private Integer type;
	private Long commentator;
	private Long gmtCreate;
	private Long gmtModified;
	private Long likeCount;
	private String content;
	private User user;
	private Integer commentCount;
}

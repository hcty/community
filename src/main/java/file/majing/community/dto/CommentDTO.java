package file.majing.community.dto;

import file.majing.community.model.User;
import lombok.Data;

/**
 * 问题回复dto
 * Created by hechuan on 2019/9/6;
 */
@Data
public class CommentDTO {
	private Long id;//回复id
	private Long parentId;//问题id
	private Integer type;//回复类型
	private Long commentator;//回复人id
	private Long gmtCreate;//创建时间
	private Long gmtModified;//修改时间
	private Long likeCount;//点赞数
	private String content;//回复内容
	private User user;//回复创建人
	private Integer commentCount;//回复总数
}

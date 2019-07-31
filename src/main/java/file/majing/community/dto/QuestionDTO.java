package file.majing.community.dto;/**
 * Created by hechuan on 2019/7/31;
 */

import file.majing.community.model.User;
import lombok.Data;

/**
 *
 */
@Data
public class QuestionDTO {
	private Integer id;
	private String title;
	private String description;
	private Long gmtCreate;
	private Long gmtModified;
	private Integer creator;
	private String tag;
	private Integer viewCount;
	private Integer commentCount;
	private Integer likeCount;
	private User user;
}

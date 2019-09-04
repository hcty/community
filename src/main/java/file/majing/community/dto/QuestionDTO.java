package file.majing.community.dto;
/**
 * Created by hechuan on 2019/7/31;
 * QuestionDTO
 */

import file.majing.community.common.CommunityTimeUtils;
import file.majing.community.model.User;
import lombok.Data;

@Data public class QuestionDTO {
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
	private String diffTime;

	/**
	 * 计算该问题是多久前发布的
	 *
	 * @Author: hechuan on 2019/9/4 17:29
	 * @param:
	 * @return:
	 */
	public String getCreateTimeStr() {
		return this.gmtModified != null && this.gmtModified > 0 ?
				CommunityTimeUtils.getDatePoor(this.gmtModified, System.currentTimeMillis()) :
				"";
	}
}

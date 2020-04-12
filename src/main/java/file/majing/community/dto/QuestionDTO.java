package file.majing.community.dto;
/**
 * Created by hechuan on 2019/7/31;
 * QuestionDTO
 */

import file.majing.community.common.CommunityTimeUtils;
import file.majing.community.model.User;
import lombok.Data;

@Data public class QuestionDTO {
	private Long id;//问题id
	private String title;//问题标题
	private String description;//问题内日哦那个
	private Long gmtCreate;//创建时间
	private Long gmtModified;//修改时间
	private Long creator;//创建用户id
	private String tag;//问题标题
	private Integer viewCount;//阅读数
	private Integer commentCount;//回复数
	private Integer likeCount;//点赞数
	private User user;//创建用户
	private String diffTime;//修改时间

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

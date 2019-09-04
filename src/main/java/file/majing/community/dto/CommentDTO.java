package file.majing.community.dto;

import lombok.Data;

import java.time.Instant;

/**
 * Created by hechuan on 2019/9/4;
 */
@Data
public class CommentDTO {
	private Long parentId;
	private String content;
	private Integer type;
}

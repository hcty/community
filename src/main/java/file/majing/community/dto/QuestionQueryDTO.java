package file.majing.community.dto;

import lombok.Data;

/**
 * Created by hechuan on 2020/4/16;
 */
@Data
public class QuestionQueryDTO {
	private String search;
	private Integer page;
	private Integer size;
}

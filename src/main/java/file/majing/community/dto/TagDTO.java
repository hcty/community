package file.majing.community.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by hechuan on 2020/4/10;
 */
@Data
public class TagDTO {
	private String categoryName;
	private List<String> tags;
}

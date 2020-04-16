package file.majing.community.mapper;

import file.majing.community.dto.QuestionQueryDTO;
import file.majing.community.model.Question;

import java.util.List;

/**
 * 自定义Mapper,扩展generator插件生成方法的不足
 */
public interface QuestionExtMapper {
	/**
	 * 增加阅读数
	 *
	 * @param record
	 * @return
	 */
	int incView(Question record);

	/**
	 * 增加回复数
	 *
	 * @param record
	 * @return
	 */
	int incCommentCount(Question record);

	/**
	 * 查询相关问题
	 *
	 * @param question
	 * @return
	 */
	List<Question> selectRelated(Question question);

	Integer countBySearch(QuestionQueryDTO questionQueryDTO);

	List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
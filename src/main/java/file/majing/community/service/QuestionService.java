package file.majing.community.service;
/**
 * Created by hechuan on 2019/7/31;
 */

import file.majing.community.dto.PaginationDTO;
import file.majing.community.dto.QuestionDTO;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import file.majing.community.mapper.QuestionExtMapper;
import file.majing.community.mapper.QuestionMapper;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.Question;
import file.majing.community.model.QuestionExample;
import file.majing.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service public class QuestionService {
	@Autowired private UserMapper userMapper;
	@Autowired private QuestionMapper questionMapper;
	@Autowired private QuestionExtMapper questionExtMapper;
	/**
	 * @Author: hechuan on 2019/8/7 22:28
	 * @param: [page, size]
	 * @return: file.majing.community.dto.PaginationDTO
	 */
	public PaginationDTO list(Integer page, Integer size) {
		PaginationDTO pagination = new PaginationDTO();
		QuestionExample questionExample = new QuestionExample();
		Integer totalCount = (int) questionMapper.countByExample(questionExample);
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		questionExample.setOrderByClause("GMT_MODIFIED");
		List<Question> questions = questionMapper
				.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;
		User user = null;
		for (Question question : questions) {
			user = userMapper.selectByPrimaryKey(question.getCreator());
			questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setQuestions(questionDTOS);
		return pagination;
	}

	public PaginationDTO list(Long userId, Integer page, Integer size) {
		PaginationDTO pagination = new PaginationDTO();
		QuestionExample questionExample = new QuestionExample();
		questionExample.createCriteria().andCreatorEqualTo(userId);
		Integer totalCount = (int) questionMapper.countByExample(questionExample);
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		questionExample.setOrderByClause("GMT_MODIFIED");
		List<Question> questions = questionMapper
				.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
		List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;
		User user = null;
		for (Question question : questions) {
			user = userMapper.selectByPrimaryKey(question.getCreator());
			questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setQuestions(questionDTOS);
		return pagination;
	}

	public QuestionDTO getById(Long id) {
		Question question = questionMapper.selectByPrimaryKey(id);
		if (question == null) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		User user = userMapper.selectByPrimaryKey(question.getCreator());
		QuestionDTO questionDTO = new QuestionDTO();
		BeanUtils.copyProperties(question, questionDTO);
		questionDTO.setUser(user);
		return questionDTO;
	}

	/**
	 * 创建或者更新问题
	 *
	 * @Author: hechuan on 2019/9/3 21:20
	 * @param:
	 * @return:
	 */
	public void createOrUpdate(Question question) {
		if (question.getId() == null) {
			question.setGmtCreate(System.currentTimeMillis());
			question.setGmtModified(question.getGmtCreate());
			question.setViewCount(0);
			question.setCommentCount(0);
			question.setLikeCount(0);
			questionMapper.insert(question);
		} else {
			question.setGmtModified(System.currentTimeMillis());
			QuestionExample questionExample = new QuestionExample();
			questionExample.createCriteria().andIdEqualTo(question.getId());
			int updated = questionMapper.updateByExampleSelective(question, questionExample);
			if (updated != 1) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
		}
	}

	/**
	 * 修改阅读数
	 * @Author: hechuan on 2019/9/4 22:40
	 * @param:
	 * @return:
	 */
	public void incView(Long id) {
		Question qestion = new Question();
		qestion.setViewCount(1);
		qestion.setId(id);
		questionExtMapper.incView(qestion);
	}
}

package file.majing.community.service;
/**
 * Created by hechuan on 2019/7/31;
 */

import file.majing.community.dto.PaginationDTO;
import file.majing.community.dto.QuestionDTO;
import file.majing.community.mapper.QuestionMapper;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.Question;
import file.majing.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service public class QuestionService {
	@Autowired UserMapper userMapper;
	@Autowired QuestionMapper questionMapper;

	/**
	 * @Author: hechuan on 2019/8/7 22:28
	 * @param: [page, size]
	 * @return: file.majing.community.dto.PaginationDTO
	 */
	public PaginationDTO list(Integer page, Integer size) {
		PaginationDTO pagination = new PaginationDTO();
		Integer totalCount = questionMapper.count();
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		List<Question> questions = questionMapper.list(offset, size);
		List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;
		User user = null;
		for (Question question : questions) {
			user = userMapper.findById(question.getCreator());
			questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setQuestions(questionDTOS);
		return pagination;
	}

	public PaginationDTO list(Integer userId, Integer page, Integer size) {
		PaginationDTO pagination = new PaginationDTO();
		Integer totalCount = questionMapper.countByUserId(userId);
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		List<Question> questions = questionMapper.listByUserId(userId, offset, size);
		List<QuestionDTO> questionDTOS = new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO = null;
		User user = null;
		for (Question question : questions) {
			user = userMapper.findById(question.getCreator());
			questionDTO = new QuestionDTO();
			BeanUtils.copyProperties(question, questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setQuestions(questionDTOS);
		return pagination;
	}
}

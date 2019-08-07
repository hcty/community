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

/**
 *
 */
@Service
public class QuestionService {
	@Autowired UserMapper userMapper;
	@Autowired QuestionMapper questionMapper;
	public PaginationDTO list(Integer page, Integer size) {
		PaginationDTO pagination = new PaginationDTO();
		Integer totalCount = questionMapper.count();
		pagination.setPagination(totalCount,page,size);
		Integer offset = size*(page-1);
		if (page < 1) {
			page = 1;
		}
		if (page > pagination.getTotalPage()) {
			page = pagination.getTotalPage();
		}
		List<Question> questions=questionMapper.list(offset,size);
		List<QuestionDTO> questionDTOS=new ArrayList<QuestionDTO>();
		QuestionDTO questionDTO=null;
		User user=null;
		for (Question question : questions) {
			user=userMapper.findById(question.getCreator());
			questionDTO=new QuestionDTO();
			BeanUtils.copyProperties(question,questionDTO);
			questionDTO.setUser(user);
			questionDTOS.add(questionDTO);
		}
		pagination.setQuestions(questionDTOS);
		return pagination;
	}
}

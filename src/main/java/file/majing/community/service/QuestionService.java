package file.majing.community.service;/**
 * Created by hechuan on 2019/7/31;
 */

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
	public List<QuestionDTO> list() {
		List<Question> questions=questionMapper.list();
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
		return questionDTOS;
	}
}

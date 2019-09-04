package file.majing.community.controller;

import file.majing.community.dto.QuestionDTO;
import file.majing.community.mapper.QuestionMapper;
import file.majing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created by hechuan on 2019/9/3;
 */
@Controller
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@GetMapping("/question/{id}")
	public String question(@PathVariable(name="id")Integer id,
			Model model){
		QuestionDTO questionDTO=questionService.getById(id);
		//累加阅读数
		questionService.incView(questionDTO.getId());
		model.addAttribute("question",questionDTO);
		return "question";
	}
}

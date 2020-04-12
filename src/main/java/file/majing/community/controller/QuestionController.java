package file.majing.community.controller;

import file.majing.community.dto.CommentDTO;
import file.majing.community.dto.QuestionDTO;
import file.majing.community.enums.CommentTypeEnum;
import file.majing.community.service.CommentService;
import file.majing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by hechuan on 2019/9/3;
 */
@Controller public class QuestionController {
	@Autowired private QuestionService questionService;
	@Autowired private CommentService commentService;

	/**
	 * 查看问题
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/question/{id}") public String question(@PathVariable(name = "id") Long id, Model model) {
		QuestionDTO questionDTO = questionService.getById(id);
		//累加阅读数
		questionService.incView(questionDTO.getId());
		List<QuestionDTO> realetedQuestions=questionService.selectReleted(questionDTO);
		List<CommentDTO> comments=commentService.listByQuestionID(id, CommentTypeEnum.QUESTION);
		model.addAttribute("question", questionDTO);
		model.addAttribute("comments", comments);
		model.addAttribute("realetedQuestions",realetedQuestions);
		return "question";
	}
}

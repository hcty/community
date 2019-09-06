package file.majing.community.controller;

import file.majing.community.dto.CommentCreateDTO;
import file.majing.community.dto.ResultDTO;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.model.Comment;
import file.majing.community.model.User;
import file.majing.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hechuan on 2019/9/4;
 */
@Controller public class CommentController {
	@Autowired private CommentService commentService;
	@ResponseBody @RequestMapping(value = "/comment", method = RequestMethod.POST) public Object post(
			@RequestBody CommentCreateDTO commentDTO,
			HttpServletRequest request) {
		User user= (User) request.getSession().getAttribute("user");
		if (user==null){
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		Comment comment = new Comment();
		comment.setParentId(commentDTO.getParentId());
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setCommentator(user.getId());
		commentService.insert(comment);
		return ResultDTO.okOff();
	}
}

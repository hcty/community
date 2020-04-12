package file.majing.community.controller;

import file.majing.community.dto.CommentCreateDTO;
import file.majing.community.dto.CommentDTO;
import file.majing.community.dto.ResultDTO;
import file.majing.community.enums.CommentTypeEnum;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.model.Comment;
import file.majing.community.model.User;
import file.majing.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by hechuan on 2019/9/4;
 */
@Controller public class CommentController {
	@Autowired private CommentService commentService;

	/**
	 * 新增回复
	 *
	 * @param commentDTO
	 * @param request
	 * @return
	 */
	@ResponseBody @RequestMapping(value = "/comment", method = RequestMethod.POST) public Object post(
			@RequestBody CommentCreateDTO commentDTO, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
		}
		if (commentDTO == null || StringUtils.isAllBlank(commentDTO.getContent())) {
			return ResultDTO.errorOf(CustomizeErrorCode.COMMENT_IS_EMPTY);
		}
		Comment comment = new Comment();
		comment.setParentId(commentDTO.getParentId());
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setCommentator(user.getId());
		commentService.insert(comment, user);
		return ResultDTO.okOff();
	}

	/**
	 * 回去回复评论
	 *
	 * @param id
	 * @return
	 */
	@ResponseBody @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET) public ResultDTO<List<CommentDTO>> comments(
			@PathVariable(name = "id") Long id) {
		List<CommentDTO> commentDTOS = commentService.listByQuestionID(id, CommentTypeEnum.COMMENT);
		return ResultDTO.okOff(commentDTOS);
	}
}

package file.majing.community.controller;

import file.majing.community.dto.CommentDTO;
import file.majing.community.mapper.CommentMapper;
import file.majing.community.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by hechuan on 2019/9/4;
 */
@Controller
public class CommentController {
	@Autowired private CommentMapper commentMapper;
	@RequestMapping(value = "/comment",method = RequestMethod.POST)
	@ResponseBody
	public Object post(@RequestBody CommentDTO commentDTO){
		Comment comment = new Comment();
		comment.setParentId(commentDTO.getParentId());//149
		comment.setContent(commentDTO.getContent());
		comment.setType(commentDTO.getType());
		comment.setGmtCreate(System.currentTimeMillis());
		comment.setGmtModified(System.currentTimeMillis());
		comment.setCommentator(324);
		comment.setLikeCount(0L);
		commentMapper.insert(comment);
		return null;
	}
}

package file.majing.community.service;

import file.majing.community.enums.CommentTypeEnum;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import file.majing.community.mapper.CommentMapper;
import file.majing.community.mapper.QuestionExtMapper;
import file.majing.community.mapper.QuestionMapper;
import file.majing.community.model.Comment;
import file.majing.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by hechuan on 2019/9/5;
 */
@Service public class CommentService {
	@Autowired private CommentMapper commentMapper;
	@Autowired private QuestionMapper questionMapper;
	@Autowired private QuestionExtMapper questionExtMapper;

	@Transactional public void insert(Comment comment) {
		if (comment.getParentId() == null || comment.getParentId() == 0) {
			throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
		}
		if (comment.getType() == null && CommentTypeEnum.isExist(comment.getType())) {
			throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
		}
		if (comment.getType().intValue() == CommentTypeEnum.COMMENT.getType().intValue()) {
			//回复评论
			Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
			if (dbComment == null) {
				throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
			}
			commentMapper.insert(comment);
		} else {
			//回复问题
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			question.setCommentCount(1);
			questionExtMapper.incCommentCount(question);
		}
	}
}

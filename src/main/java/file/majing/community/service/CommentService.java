package file.majing.community.service;

import file.majing.community.dto.CommentDTO;
import file.majing.community.enums.CommentTypeEnum;
import file.majing.community.enums.NotificationStatusEnum;
import file.majing.community.enums.NotificationTypeEnum;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import file.majing.community.mapper.*;
import file.majing.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by hechuan on 2019/9/5;
 */
@Service public class CommentService {
	@Autowired private CommentMapper commentMapper;
	@Autowired private QuestionMapper questionMapper;
	@Autowired private QuestionExtMapper questionExtMapper;
	@Autowired private UserMapper userMapper;
	@Autowired private CommentExtMapper commentExtMapper;
	@Autowired private NotificationMapper notificationMapper;

	/**
	 * 回复问题或评论
	 *
	 * @param comment     回复内容
	 * @param commentator 回复人
	 */
	@Transactional public void insert(Comment comment, User commentator) {
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
			//回复问题
			Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			//增加评论数
			Comment parentComment = new Comment();
			parentComment.setId(comment.getParentId());
			parentComment.setCommentCount(1);
			commentExtMapper.incCommentCount(parentComment);
			//增加通知
			createNofify(comment.getCommentator(), dbComment.getCommentator(), commentator.getName(),
					question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
		} else {
			//回复问题
			Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
			if (question == null) {
				throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
			}
			commentMapper.insert(comment);
			question.setCommentCount(1);
			questionExtMapper.incCommentCount(question);
			//增加通知
			createNofify(comment.getCommentator(), question.getCreator(), commentator.getName(), question.getTitle(),
					NotificationTypeEnum.REPLY_QUESTION, question.getId());
		}
	}

	/**
	 * 新增通知
	 *
	 * @param commentator      回复或评论人id
	 * @param receiver         通知接受人id
	 * @param notifileName     回复或评论人名称
	 * @param outerTitle       评论或回复问题标题
	 * @param notificationType 通知类型，评论/回复
	 * @param outerId          评论/回复的问题id
	 */
	private void createNofify(Long commentator, Long receiver, String notifileName, String outerTitle,
			NotificationTypeEnum notificationType, Long outerId) {
		Notification notification = new Notification();
		notification.setGmtCreate(System.currentTimeMillis());
		notification.setType(notificationType.getType());
		notification.setOuterid(outerId);
		notification.setNitifier(commentator);
		notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
		notification.setReceiver(receiver);
		notification.setNotifierName(notifileName);
		notification.setOuterTitle(outerTitle);
		notificationMapper.insert(notification);
	}

	/**
	 * 回复内容展示
	 *
	 * @param id   问题id
	 * @param type 回复类型，1:问题回复 2:评论回复
	 * @return
	 */
	public List<CommentDTO> listByQuestionID(Long id, CommentTypeEnum type) {
		CommentExample commentExample = new CommentExample();
		commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
		commentExample.setOrderByClause("GMT_CREATE desc");
		List<Comment> comments = commentMapper.selectByExample(commentExample);
		if (comments.size() == 0) {
			return new ArrayList<>();
		}
		//获取去重的评论人
		Set<Long> commentors = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
		List<Long> userIds = commentors.stream().collect(Collectors.toList());
		//获取评论人，并转换为map
		UserExample userExample = new UserExample();
		userExample.createCriteria().andIdIn(userIds);
		List<User> users = userMapper.selectByExample(userExample);
		Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, user -> user));
		//转换comment为commentDTO
		List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
			CommentDTO commentDTO = new CommentDTO();
			BeanUtils.copyProperties(comment, commentDTO);
			commentDTO.setUser(userMap.get(comment.getCommentator()));
			return commentDTO;
		}).collect(Collectors.toList());
		return commentDTOS;
	}
}

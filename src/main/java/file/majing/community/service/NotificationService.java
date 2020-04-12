package file.majing.community.service;

import file.majing.community.dto.NotificationDTO;
import file.majing.community.dto.PaginationDTO;
import file.majing.community.enums.NotificationStatusEnum;
import file.majing.community.enums.NotificationTypeEnum;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import file.majing.community.mapper.NotificationMapper;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.Notification;
import file.majing.community.model.NotificationExample;
import file.majing.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hechuan on 2020/4/11;
 */
@Service public class NotificationService {
	@Autowired NotificationMapper notificationMapper;
	@Autowired UserMapper userMapper;

	/**
	 * 分页查询用户通知消息
	 *
	 * @param userId 用户id
	 * @param page   页码
	 * @param size   每页总数
	 * @return
	 */
	public PaginationDTO list(Long userId, Integer page, Integer size) {
		PaginationDTO<NotificationDTO> pagination = new PaginationDTO<>();
		NotificationExample notificationExample = new NotificationExample();
		notificationExample.createCriteria().andReceiverEqualTo(userId);
		Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
		Integer totalPage = totalCount % size == 0 ? totalCount / size : totalCount / size + 1;
		Integer offset = size * (page - 1);
		if (page < 1) {
			page = 1;
		}
		if (page > totalPage) {
			page = totalPage;
		}
		pagination.setPagination(totalPage, page);
		notificationExample.setOrderByClause("GMT_CREATE desc");
		List<Notification> notifications = notificationMapper
				.selectByExampleWithRowbounds(notificationExample, new RowBounds(offset, size));
		if (notifications.size() == 0) {
			return pagination;
		}
		List<NotificationDTO> notificationDTOS = new ArrayList<NotificationDTO>();
		for (Notification notification : notifications) {
			NotificationDTO dto = new NotificationDTO();
			BeanUtils.copyProperties(notification, dto);
			dto.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
			notificationDTOS.add(dto);
		}
		pagination.setData(notificationDTOS);
		return pagination;
	}

	/**
	 * 查询用户的未读消息数
	 *
	 * @param userId 用户id
	 * @return
	 */
	public Long unreadCount(Long userId) {
		NotificationExample example = new NotificationExample();
		example.createCriteria().andReceiverEqualTo(userId).andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
		return notificationMapper.countByExample(example);
	}

	/**
	 * 读取消息
	 *
	 * @param id   消息id
	 * @param user 当前用户
	 * @return
	 */
	public NotificationDTO read(Long id, User user) {
		Notification notification = notificationMapper.selectByPrimaryKey(id);
		if (notification == null) {
			throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
		}
		if (!notification.getReceiver().equals(user.getId())) {
			throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
		}
		notification.setStatus(NotificationStatusEnum.READ.getStatus());
		notificationMapper.updateByPrimaryKey(notification);//更改消息为已读
		NotificationDTO notificationDTO = new NotificationDTO();
		BeanUtils.copyProperties(notification, notificationDTO);
		notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
		return notificationDTO;
	}
}

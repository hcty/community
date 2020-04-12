package file.majing.community.controller;

import file.majing.community.dto.NotificationDTO;
import file.majing.community.enums.NotificationTypeEnum;
import file.majing.community.model.User;
import file.majing.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hechuan on 2020/4/11;
 */
@Controller public class NotificationController {
	@Autowired NotificationService notificationService;

	@GetMapping("/notification/{id}") public String notification(@PathVariable(name = "id") Long id, Model model,
			HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		NotificationDTO notificationDTO = notificationService.read(id, user);//读取当前消息，并将该消息改为已读
		Long unreadCount = notificationService.unreadCount(user.getId());
		request.getSession().setAttribute("unreadCount", unreadCount);//将未读消息数放到session中
		if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
				|| NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
			return "redirect:/question/" + notificationDTO.getOuterid();
		}
		return "redirect:/";
	}
}

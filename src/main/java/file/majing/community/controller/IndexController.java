package file.majing.community.controller;

import file.majing.community.dto.PaginationDTO;
import file.majing.community.model.User;
import file.majing.community.service.NotificationService;
import file.majing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller public class IndexController {
	@Autowired private QuestionService questionService;
	@Autowired private NotificationService notificationService;
	@GetMapping("/") public String index(HttpServletRequest request, Model model,
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "4") Integer size) {
		PaginationDTO pagination = questionService.list(page, size);
		model.addAttribute("pagination", pagination);
		User user = (User) request.getSession().getAttribute("user");
		if (user != null) {
			Long unreadCount=notificationService.unreadCount(user.getId());
			request.getSession().setAttribute("unreadCount",unreadCount);
		}
		return "index";
	}
}

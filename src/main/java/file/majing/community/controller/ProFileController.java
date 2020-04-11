package file.majing.community.controller;

import file.majing.community.dto.PaginationDTO;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.User;
import file.majing.community.service.NotificationService;
import file.majing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by hechuan on 2019/8/7;
 */
@Controller public class ProFileController {
	@Autowired private UserMapper userMapper;
	@Autowired private QuestionService questionService;
	@Autowired private NotificationService notificationService;

	@GetMapping("/profile/{action}") public String profile(@PathVariable(name = "action") String action, Model model,
			HttpServletRequest request, @RequestParam(name = "page", defaultValue = "1") Integer page,
			@RequestParam(name = "size", defaultValue = "5") Integer size) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "redirect:/";
		}
		if ("questions".equals(action)) {
			model.addAttribute("section", "questions");
			model.addAttribute("sectionName", "我的问题");
			PaginationDTO pagination = questionService.list(user.getId(), page, size);
			model.addAttribute("pagination", pagination);
		} else if ("replies".equals(action)) {
			model.addAttribute("section", "replies");
			model.addAttribute("sectionName", "最新回复");
			PaginationDTO notification=notificationService.list(user.getId(), page, size);
			Long unreadCount=notificationService.unreadCount(user.getId());
			model.addAttribute("pagination", notification);
			model.addAttribute("unreadCount", unreadCount);
		}
		return "profile";
	}
}

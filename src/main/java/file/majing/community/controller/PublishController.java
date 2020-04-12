package file.majing.community.controller;

import file.majing.community.cache.TagCache;
import file.majing.community.dto.QuestionDTO;
import file.majing.community.model.Question;
import file.majing.community.model.User;
import file.majing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller public class PublishController {
	@Autowired private QuestionService questionService;

	/**
	 * 查询问题详情
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/publish/{id}") public String edit(@PathVariable(name = "id") Long id, Model model) {
		QuestionDTO question = questionService.getById(id);
		model.addAttribute("title", question.getTitle());
		model.addAttribute("description", question.getDescription());
		model.addAttribute("tag", question.getTag());
		model.addAttribute("id", id);
		model.addAttribute("tags", TagCache.get());
		return "publish";
	}

	/**
	 * 点击发布按钮跳转
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/publish") public String publish(Model model) {
		model.addAttribute("tags", TagCache.get());
		return "publish";
	}

	/**
	 * 新增或修改问题
	 *
	 * @param title       问题标题
	 * @param description 问题内容
	 * @param tag         问题标签
	 * @param id          问题id
	 * @param request
	 * @param model
	 * @return
	 */
	@PostMapping("/publish") public String doPublish(@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "tag", required = false) String tag,
			@RequestParam(value = "id", required = false) Long id, HttpServletRequest request, Model model) {
		model.addAttribute("title", title);
		model.addAttribute("description", description);
		model.addAttribute("tag", tag);
		model.addAttribute("tags", TagCache.get());
		if (!StringUtils.hasLength(title)) {
			model.addAttribute("error", "标题不能为空");
			return "publish";
		}
		if (!StringUtils.hasLength(description)) {
			model.addAttribute("error", "问题补充不能为空");
			return "publish";
		}
		if (!StringUtils.hasLength(tag)) {
			model.addAttribute("error", "标签不能为空");
			return "publish";
		}
		String invalid = TagCache.filterInvalid(tag);
		if (StringUtils.hasLength(invalid)) {
			model.addAttribute("error", "输入非法标签：" + invalid);
			return "publish";
		}
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			model.addAttribute("error", "用户未登录");
			return "publish";
		}
		Question question = new Question();
		question.setTitle(title);
		question.setDescription(description);
		question.setTag(tag);
		question.setCreator(user.getId());
		question.setId(id);
		questionService.createOrUpdate(question);
		return "redirect:/";
	}
}

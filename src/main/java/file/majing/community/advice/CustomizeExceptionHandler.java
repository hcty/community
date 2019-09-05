package file.majing.community.advice;

import com.alibaba.fastjson.JSON;
import file.majing.community.dto.ResultDTO;
import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by hechuan on 2019/9/4;
 */
@ControllerAdvice public class CustomizeExceptionHandler {

	/**异常跳转,ajax验证错误信息通过response跳转，其他异常跳转error.html
	 * @Author: hechuan on 2019/9/5 21:59
	 * @param: [request, e, model, response]
	 * @return: org.springframework.web.servlet.ModelAndView
	 */
	@ExceptionHandler(Exception.class) ModelAndView handle(HttpServletRequest request, Throwable e, Model model,
			HttpServletResponse response) {
		String contentType = request.getContentType();
		if ("application/json".equals(contentType)) {
			ResultDTO resultDTO = null;
			if (e instanceof CustomizeException) {
				resultDTO = ResultDTO.errorOf((CustomizeException) e);
			} else {
				resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
			}
			try {
				response.setContentType("application/json");
				response.setStatus(200);
				response.setCharacterEncoding("UTF-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(resultDTO));
				writer.close();
			} catch (IOException ex) {
			}
			return null;
		} else {
			if (e instanceof CustomizeException) {
				model.addAttribute("message", e.getMessage());
			} else {
				model.addAttribute("message", CustomizeErrorCode.SYS_ERROR.getMessage());
			}
			return new ModelAndView("error");
		}
	}
}

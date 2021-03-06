package file.majing.community.interceptor;

import file.majing.community.mapper.UserMapper;
import file.majing.community.model.User;
import file.majing.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * session拦截器,判断用户是否登录
 * Created by hechuan on 2019/9/2;
 */
@Service public class SessionInterceptor implements HandlerInterceptor {
	@Autowired private UserMapper userMapper;

	@Override public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Cookie[] cookies = request.getCookies();
		if (null != cookies && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if ("token".equals(cookie.getName())) {
					String token = cookie.getValue();
					UserExample userExample = new UserExample();
					userExample.createCriteria().andTokenEqualTo(token);
					List<User> users = userMapper.selectByExample(userExample);
					if (users.size() != 0) {
						request.getSession().setAttribute("user", users.get(0));
					}
					break;
				}
			}
		}
		return true;
	}

	@Override public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) throws Exception {

	}
}

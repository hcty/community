package file.majing.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by hechuan on 2019/9/2;
 */
@Configuration  public class WebConfig implements WebMvcConfigurer {

	@Autowired private SessionInterceptor sessionInterceptor;

	@Override public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");
	}

}

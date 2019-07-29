package file.majing.community.controller;

import file.majing.community.dto.AccessTokenDTO;
import file.majing.community.dto.GItHubUser;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.User;
import file.majing.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller public class AuthorizeController {
	@Autowired
	private GitHubProvider gitHubProvider;

	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String secret;
	@Value("${github.redirect.uri}")
	private String redirectUri;
	@Autowired
	private UserMapper userMapper;
	@GetMapping("/callback") public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state,
			HttpServletRequest request) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		String accessToken=gitHubProvider.getAccessToken(accessTokenDTO);
		GItHubUser gItHubUser=gitHubProvider.getUser(accessToken);
		if(null!=gItHubUser){
			System.out.println(gItHubUser.getName());
			//登录成功，写cookie,session
			request.getSession().setAttribute("user",gItHubUser);
			User user = new User();
			user.setToken(UUID.randomUUID().toString());
			user.setName(gItHubUser.getName());
			user.setAccountId(String.valueOf(gItHubUser.getId()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userMapper.insert(user);
			return "redirect:/";
		}else{
			//登录失败,重新登录
			return "redirect:/";
		}
	}
}

package file.majing.community.controller;

import file.majing.community.dto.AccessTokenDTO;
import file.majing.community.dto.BaiDuAccessTokenDTO;
import file.majing.community.dto.BaiDuUser;
import file.majing.community.dto.GItHubUser;
import file.majing.community.mapper.UserMapper;
import file.majing.community.model.User;
import file.majing.community.provider.GitHubProvider;
import file.majing.community.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
@Slf4j
@Controller public class AuthorizeController {
	@Autowired
	private GitHubProvider gitHubProvider;

	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String secret;
	@Value("${github.redirect.uri}")
	private String redirectUri;
	@Value("${baidu.client.id}")
	private String bdClientId;
	@Value("${baidu.client.secret}")
	private String bdSecret;
	@Value("${baidu.redirect.uri}")
	private String bdRedirectUri;
	@Autowired
	private UserService userService;
	@GetMapping("/callback") public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state,
			HttpServletResponse response) {
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
			//.getSession().setAttribute("user",gItHubUser);
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(gItHubUser.getName());
			user.setAccountId(String.valueOf(gItHubUser.getId()));
			user.setAvatarUrl(gItHubUser.getAvatarUrl());
			userService.createOrUpdate(user);
			response.addCookie(new Cookie("token",token));
			log.info("github user login success,{}",gItHubUser.getName());
			return "redirect:/";
		}else{
			log.error("callback  get github error,{}",gItHubUser);
			//登录失败,重新登录
			return "redirect:/";
		}
	}
	@GetMapping("/bdcallback")
	public String bdcallback (@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state,
			HttpServletResponse response){
		System.out.println(code);
		BaiDuAccessTokenDTO accessTokenDTO = new BaiDuAccessTokenDTO();
		accessTokenDTO.setClient_id(bdClientId);
		accessTokenDTO.setClient_secret(bdSecret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(bdRedirectUri);
		accessTokenDTO.setGrant_type("authorization_code");
		String accessToken=gitHubProvider.getBaiDuAccessToken(accessTokenDTO);
		BaiDuUser baiDuUser=gitHubProvider.getBaiDuUser(accessToken);
		if(null!=baiDuUser){
			System.out.println(baiDuUser.getUname());
			//登录成功，写cookie,session
			//.getSession().setAttribute("user",gItHubUser);
			User user = new User();
			String token = UUID.randomUUID().toString();
			user.setToken(token);
			user.setName(baiDuUser.getUname());
			user.setAccountId(String.valueOf(baiDuUser.getUid()));
			user.setGmtCreate(System.currentTimeMillis());
			user.setGmtModified(user.getGmtCreate());
			userService.createOrUpdate(user);
			response.addCookie(new Cookie("token",token));
			return "redirect:/";
		}else{
			//登录失败,重新登录
			return "redirect:/";
		}
	}
	@GetMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response){
		request.getSession().removeAttribute("user");
		request.getSession().removeAttribute("unreadCount");//一处session中的未读消息数
		Cookie cookie=new Cookie("token",null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		return "redirect:/";
	}
}

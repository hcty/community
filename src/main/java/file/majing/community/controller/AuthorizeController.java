package file.majing.community.controller;

import file.majing.community.dto.AccessTokenDTO;
import file.majing.community.dto.GItHubUser;
import file.majing.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller public class AuthorizeController {
	@Autowired
	private GitHubProvider gitHubProvider;
	@GetMapping("/callback") public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setClient_id("dcf0854013085140d0d4");
		accessTokenDTO.setClient_secret("a41a2c578d393776f7a6ac230382eaacca8b95ff");
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
		accessTokenDTO.setState(state);
		String accessToken=gitHubProvider.getAccessToken(accessTokenDTO);
		GItHubUser user=gitHubProvider.getUser(accessToken);
		System.out.println(user.getName());
		return "index";
	}
}

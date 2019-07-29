package file.majing.community.controller;

import file.majing.community.dto.AccessTokenDTO;
import file.majing.community.dto.GItHubUser;
import file.majing.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller public class AuthorizeController {
	@Autowired
	private GitHubProvider gitHubProvider;

	@Value("${github.client.id}")
	private String clientId;
	@Value("${github.client.secret}")
	private String secret;
	@Value("${github.redirect.uri}")
	private String redirectUri;
	@GetMapping("/callback") public String callback(@RequestParam(name = "code") String code,
			@RequestParam(name = "state") String state) {
		AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
		accessTokenDTO.setClient_id(clientId);
		accessTokenDTO.setClient_secret(secret);
		accessTokenDTO.setCode(code);
		accessTokenDTO.setRedirect_uri(redirectUri);
		accessTokenDTO.setState(state);
		String accessToken=gitHubProvider.getAccessToken(accessTokenDTO);
		GItHubUser user=gitHubProvider.getUser(accessToken);
		System.out.println(user.getName());
		return "index";
	}
}

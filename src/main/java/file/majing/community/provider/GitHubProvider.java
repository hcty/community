package file.majing.community.provider;

import com.alibaba.fastjson.JSON;
import file.majing.community.dto.AccessTokenDTO;
import file.majing.community.dto.GItHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * GitHub 第三方提供者
 *
 * @Component将该类添加到spring上下文中，自动实例化该类，
 */
@Component public class GitHubProvider {
	public String getAccessToken(AccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
		try (Response response = client.newCall(request).execute()) {
			String result=response.body().string();
			System.out.println(result);
			String token=result.split("&")[0].split("=")[1];
			return token;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public GItHubUser getUser(String accessToken){
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.url("https://api.github.com/user?access_token="+accessToken)
				.build();
		try (Response response = client.newCall(request).execute()) {
			String string=response.body().string();
			GItHubUser gItHubUser = JSON.parseObject(string, GItHubUser.class);
			return gItHubUser;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}

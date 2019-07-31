package file.majing.community.provider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import file.majing.community.dto.AccessTokenDTO;
import file.majing.community.dto.BaiDuAccessTokenDTO;
import file.majing.community.dto.BaiDuUser;
import file.majing.community.dto.GItHubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

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

	public String getBaiDuAccessToken(BaiDuAccessTokenDTO accessTokenDTO) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		System.out.println("json:"+JSON.toJSONString(accessTokenDTO));
		RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
		Request request = new Request.Builder().url("https://openapi.baidu.com/oauth/2.0/token").post(body).build();
		System.out.println(request.url());
		try (Response response = client.newCall(request).execute()) {
			String result=response.body().string();
			System.out.println(result);
			JSONObject jsonObject=JSONObject.parseObject(result);
			String token = jsonObject.containsKey("access_token") == true ? jsonObject.getString("access_token"):null;
			return token;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public BaiDuUser getBaiDuUser(String accessToken) {
		MediaType mediaType = MediaType.get("application/json; charset=utf-8");
		OkHttpClient client = new OkHttpClient();
		String str="{\"access_token:"+accessToken+"}";
		RequestBody body = RequestBody.create(mediaType,str );
		Request request = new Request.Builder()
				.url("https://openapi.baidu.com/rest/2.0/passport/users/getLoggedInUser")
				.post(body).build();
		try (Response response = client.newCall(request).execute()) {
			String string=response.body().string();
			BaiDuUser baiDuUser = JSON.parseObject(string, BaiDuUser.class);
			return baiDuUser;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}

package file.majing.community.cache;

import file.majing.community.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 常用问题标签缓存
 * Created by hechuan on 2020/4/10;
 */
public class TagCache {
	public static List<TagDTO> get() {
		ArrayList<TagDTO> tagDTOS = new ArrayList<TagDTO>();
		TagDTO program = new TagDTO();
		program.setCategoryName("开发语言");
		program.setTags(
				Arrays.asList("js", "css", "html", "php", "java", "javascript", "c", "c++", "less", "html5", "node.js",
						"python", "asp.net", "node.js", "python", "asp.net", "node.js", "python", "asp.net", "node.js",
						"python", "asp.net", "node.js", "python", "asp.net"));
		tagDTOS.add(program);

		TagDTO framework = new TagDTO();
		framework.setCategoryName("平台框架");
		framework.setTags(
				Arrays.asList("spring", "laravel", "express", "diango", "yll", "struts", "laravel", "express", "diango",
						"yll", "struts", "laravel", "express", "diango", "yll", "struts"));
		tagDTOS.add(framework);

		TagDTO server = new TagDTO();
		server.setCategoryName("服务器");
		server.setTags(Arrays.asList("linux", "nginx", "tomcat", "docker", "unix", "centos", "apache", "docker", "unix",
				"centos", "apache", "docker", "unix", "centos", "apache"));
		tagDTOS.add(server);

		TagDTO database = new TagDTO();
		database.setCategoryName("数据库");
		database.setTags(
				Arrays.asList("mysql", "sql", "redis", "nosql", "sqllite", "postgresql", "json", "redis", "nosql",
						"sqllite", "postgresql", "json", "redis", "nosql", "sqllite", "postgresql", "json"));
		tagDTOS.add(database);

		TagDTO tool = new TagDTO();
		tool.setCategoryName("开发工具");
		tool.setTags(
				Arrays.asList("git", "github", "studio", "code", "vim", "intellij-idea", "sublime-text", "code", "vim",
						"intellij-idea", "sublime-text", "code", "vim", "intellij-idea", "sublime-text",
						"visual-studio-code"));
		tagDTOS.add(tool);
		return tagDTOS;
	}

	/**
	 * 筛选未在常用标签列表中的标签
	 *
	 * @param tags 用户输入标签
	 * @return
	 */
	public static String filterInvalid(String tags) {
		List<String> tagList = get().stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
		String invalid = Arrays.stream(StringUtils.split(tags, ",")).filter(t -> !tagList.contains(t))
				.collect(Collectors.joining(","));
		return invalid;
	}
}

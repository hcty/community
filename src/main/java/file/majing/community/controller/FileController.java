package file.majing.community.controller;

import file.majing.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by hechuan on 2020/4/13;
 */
@Controller public class FileController {
	@ResponseBody @RequestMapping(value = "/file/upload") public FileDTO upload() {
		FileDTO fileDTO = new FileDTO();
		fileDTO.setSuccess(1);
		fileDTO.setUrl("/images/t.png");
		fileDTO.setMessage("上传成功");
		return fileDTO;
	}
}

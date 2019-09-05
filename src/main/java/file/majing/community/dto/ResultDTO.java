package file.majing.community.dto;

import file.majing.community.exception.CustomizeErrorCode;
import file.majing.community.exception.CustomizeException;
import lombok.Data;

/**
 * Created by hechuan on 2019/9/5;
 */
@Data
public class ResultDTO {
	private Integer code;
	private String message;

	public static ResultDTO errorOf(Integer code, String message) {
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(code);
		resultDTO.setMessage(message);
		return resultDTO;
	}

	public static ResultDTO errorOf(CustomizeErrorCode errorCode) {
		return errorOf(errorCode.getCode(),errorCode.getMessage());
	}
	public static ResultDTO okOff(){
		ResultDTO resultDTO = new ResultDTO();
		resultDTO.setCode(200);
		resultDTO.setMessage("成功");
		return resultDTO;
	}

	public static ResultDTO errorOf(CustomizeException e) {
		return errorOf(e.getCode(),e.getMessage());
	}
}

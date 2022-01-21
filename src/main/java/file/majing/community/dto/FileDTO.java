package file.majing.community.dto;

import lombok.Data;

/**
 * Created by hechuan on 2020/4/13;
 */
@Data
public class FileDTO {
	private int success;// 0 表示上传失败，1 表示上传成功
	private String message;//提示的信息，上传成功或上传失败及错误信息等。
	private String url;//图片地址,上传成功时才返回

}
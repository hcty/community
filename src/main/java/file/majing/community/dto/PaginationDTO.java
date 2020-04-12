package file.majing.community.dto;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMBinOp;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据分页dto
 * Created by hechuan on 2019/8/7;
 */
@Data public class PaginationDTO<T> {
	private List<T> data;//分页展示的数据
	private boolean showPrevious;//上一页
	private boolean showFirstPage;//首页
	private boolean showNext;//下一页
	private boolean showEndPage;//尾页
	private Integer page;//当前页
	private List<Integer> pages = new ArrayList<Integer>(7);//展示的页码集合
	private Integer totalPage;//总页数

	public void setPagination(Integer totalPage, Integer page) {
		this.totalPage = totalPage;
		this.page = page;
		pages.add(page);
		for (int i = 1; i <= 3; i++) {
			if (page - i > 0) {
				pages.add(0, page - i);
			}
			if (page + i <= totalPage) {
				pages.add(page + i);
			}
		}
		//是否展示上一页
		if (page == 1) {
			showPrevious = false;
		} else {
			showPrevious = true;
		}
		//是否展示下一页
		if (page.equals(totalPage)) {
			showNext = false;
		} else {
			showNext = true;
		}
		//是否展示第一页
		if (pages.contains(1)) {
			showFirstPage = false;
		} else {
			showFirstPage = true;
		}
		//是否展示最后一页
		if (pages.contains(totalPage)) {
			showEndPage = false;
		} else {
			showEndPage = true;
		}
	}
}

package file.majing.community.solver;

import file.majing.community.dto.QuestionDTO;
import file.majing.community.service.QuestionService;
import file.majing.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * Created by hechuan on 2019/12/20;
 */
public abstract class InspectionSolver {
	@Resource public UserService userService;
	@Resource public QuestionService questionService;

	public abstract void solve();

	public abstract String supports();
}

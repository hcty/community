package file.majing.community.solver.impl;

import file.majing.community.solver.InspectionSolver;
import org.springframework.stereotype.Component;

/**
 * Created by hechuan on 2019/12/20;
 */
@Component
public class ChangeWareSolver extends InspectionSolver {
	@Override public void solve() {
		System.out.println("订单开始转仓了。。。");
	}

	@Override public String supports() {
		return "转仓";
	}
}

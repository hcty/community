package file.majing.community;

import file.majing.community.dto.QuestionDTO;
import file.majing.community.interceptor.InspectionSolverChoorser;
import file.majing.community.solver.InspectionSolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by hechuan on 2019/12/20;
 */
@RunWith(SpringJUnit4ClassRunner.class) @SpringBootTest(classes = CommunityApplication.class) public class JunitTest {
	@Autowired private InspectionSolverChoorser choorser;

	@Test public void test() {
		String type = "转仓";
		InspectionSolver solver = choorser.choose(type);
		if (solver == null) {
			System.out.println("出错了");
		} else {
			solver.solve();
			QuestionDTO questionDTO = solver.questionService.getById(2l);
			System.out.println(questionDTO.getTitle());
		}
	}
}

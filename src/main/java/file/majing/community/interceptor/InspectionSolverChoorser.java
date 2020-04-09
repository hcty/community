package file.majing.community.interceptor;

import file.majing.community.solver.InspectionSolver;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hechuan on 2019/12/20;
 */
@Component
public class InspectionSolverChoorser implements ApplicationContextAware {
	private ApplicationContext context;
	private Map<String, InspectionSolver> chooseMap=new HashMap<>();
	@Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context=applicationContext;
	}
	public InspectionSolver choose(String type){
		return chooseMap.get(type);
	}
	@PostConstruct
	public void regedic(){
		Map<String, InspectionSolver> solverMap=context.getBeansOfType(InspectionSolver.class);
		for (InspectionSolver solver : solverMap.values()) {
			chooseMap.put(solver.supports(),solver);
		}
	}
}


package controllers.HandyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.FixUpTask;
import domain.HandyWorker;
import security.LoginService;
import services.FixUpTaskService;
import services.HandyWorkerService;

@Controller
@RequestMapping("/fixUpTask/handyworker")
public class FixUpTaskHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor---------------------------------------------------------

	public FixUpTaskHandyWorkerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixuptasks;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		fixuptasks = this.fixUpTaskService.findFixUpTaskByCustomerId(handyWorker.getId());
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixuptasks);
		result.addObject("requestURI", "fixUpTask/handyworker/list.do");
		return result;
	}

}


package controllers.HandyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyworker")
public class ApplicationHandyWorkerController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor---------------------------------------------------------

	public ApplicationHandyWorkerController() {
		super();
	}
	//List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Application> apps;

		apps = this.applicationService.findAll();
		result = new ModelAndView("tutorial/list");
		result.addObject("applications", apps);
		result.addObject("requestURI", "/list.do");
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Application app;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		app = this.applicationService.create(a.getId());
		result = this.createEditModelAndView(app);
		result.addObject("handyWorkerId", a.getId());

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "application/handyworker/edit.do");

		return result;
	}
}

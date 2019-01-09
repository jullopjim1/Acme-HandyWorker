
package controllers.HandyWorker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.HandyWorker;

@Controller
@RequestMapping("/application/handyWorker")
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
		final Collection<Application> applications;

		applications = this.applicationService.findAll();
		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "/list.do");
		return result;
	}

	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId) {
		final ModelAndView result;

		final Application applications = this.applicationService.findOne(applicationId);

		result = this.createEditModelAndView(applications);
		result.addObject("isRead", true);
		result.addObject("requestURI", "/show.do?applicationId=" + applicationId);

		return result;

	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Application applications;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		applications = this.applicationService.create(a.getId());
		result = this.createEditModelAndView(applications);
		result.addObject("handyWorkerId", a.getId());

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application applications;

		applications = this.applicationService.findOne(applicationId);
		Assert.notNull(applications);
		result = this.createEditModelAndView(applications);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application applications, final BindingResult binding) {

		ModelAndView result;
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		if (binding.hasErrors())
			result = this.createEditModelAndView(applications);
		else
			try {
				this.applicationService.save(applications);
				result = new ModelAndView("redirect:/application/handyworker/list.do?=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(applications, "application.commit.error");

			}
		return result;
	}

	//DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Application applications, final BindingResult binding) {

		ModelAndView result;
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		try {
			this.applicationService.delete(applications);
			result = new ModelAndView("redirect:list.do?handyWorkerId=" + a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(applications, "application.commit.error");
		}
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

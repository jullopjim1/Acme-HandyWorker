
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
import domain.Sponsorship;
import domain.Tutorial;

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
		Tutorial tutorial;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		tutorial = this.tutorialService.create(a.getId());
		result = this.createEditModelAndView(tutorial);
		result.addObject("handyWorkerId", a.getId());

		return result;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId) {
		ModelAndView result;
		Tutorial tutorial;

		tutorial = this.tutorialService.findOne(tutorialId);
		Assert.notNull(tutorial);
		result = this.createEditModelAndView(tutorial);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial);
		else
			try {
				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:/tutorial/handyworker/list.do?=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");

			}
		return result;
	}

	//DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:list.do?handyWorkerId=" + a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
		}
		return result;
	}
	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String message) {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findAll();
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("handyWorkerId", a.getId());
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "tutorial/handyworker/edit.do");

		return result;
	}
}

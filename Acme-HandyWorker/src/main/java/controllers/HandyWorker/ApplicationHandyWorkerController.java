package controllers.HandyWorker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.ApplicationService;
import services.ConfigurationService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Application;
import domain.HandyWorker;
import forms.ApplicationColour;

@Controller
@RequestMapping("/application/handyworker")
public class ApplicationHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private ConfigurationService configurationService;

	// Constructor---------------------------------------------------------

	public ApplicationHandyWorkerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Application> applications;

		final HandyWorker h = this.handyWorkerService
				.findHandyWorkerByUserAccount(LoginService.getPrincipal()
						.getId());
		final int handyWorkerId = h.getId();
		applications = this.applicationService
				.findApplicationByHandyWorkerId(handyWorkerId);

		Date actualDate = new Date();
		Collection<ApplicationColour> applicationsColour = new ArrayList<ApplicationColour>();
		for (Application a : applications) {
			ApplicationColour apc = new ApplicationColour();
			apc.setApplication(a);
			String color = "PENDING";
			if (a.getStatus().equals("ACCEPTED")) {
				color = "ACCEPTED";
			} else if (a.getStatus().equals("REJECTED")) {
				color = "REJECTED";
			} else if ((a.getStatus().equals("PENDING"))
					&& (a.getFixUpTask().getDeadline().before(actualDate))) {
				color = "PENDINGANDPASSED";
			}
			apc.setColor(color);
			applicationsColour.add(apc);
		}

		result = new ModelAndView("application/list");
		result.addObject("applications", applicationsColour);
		result.addObject("handyId", handyWorkerId);
		result.addObject("requestURI", "/list.do");

		return result;
	}

	// Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final HandyWorker h = this.handyWorkerService
				.findHandyWorkerByUserAccount(LoginService.getPrincipal()
						.getId());
		final Application application = this.applicationService
				.findOne(applicationId);
		try {
			Assert.notNull(application);
			Assert.isTrue(application.getHandyWorker().equals(h));
			result = this.createEditModelAndView(application);
			result.addObject("isRead", true);
			result.addObject("requestURI", "/show.do?applicationId="
					+ applicationId);

		} catch (final Throwable e) {
			result = new ModelAndView(
					"redirect:/application/handyworker/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexist");
			else if (!application.getHandyWorker().equals(h))
				redirectAttrs.addFlashAttribute("message",
						"application.error.noHandy");
		}

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		Application applications;

		final HandyWorker a = this.handyWorkerService
				.findHandyWorkerByUserAccount(LoginService.getPrincipal()
						.getId());
		applications = this.applicationService.create(fixUpTaskId);
		result = this.createEditModelAndView(applications);
		result.addObject("handyWorkerId", a.getId());

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Application application;
		application = this.applicationService.findOne(applicationId);
		final HandyWorker h = this.handyWorkerService
				.findHandyWorkerByUserAccount(LoginService.getPrincipal()
						.getId());
		try {
			Assert.notNull(application);
			Assert.isTrue(application.getHandyWorker().equals(h));
			Assert.isTrue(application.getStatus().equals("PENDING"));
			result = this.createEditModelAndView(application);

		} catch (final Throwable e) {
			result = new ModelAndView(
					"redirect:/application/handyworker/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexist");
			else if (!application.getHandyWorker().equals(h))
				redirectAttrs.addFlashAttribute("message",
						"application.error.noHandy");
			else if(!(application.getStatus().equals("PENDING"))){
				redirectAttrs.addFlashAttribute("message",
						"application.error.statusNoPending");
			}
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application applications,
			final BindingResult binding) {

		ModelAndView result;

		final HandyWorker a = this.handyWorkerService
				.findHandyWorkerByUserAccount(LoginService.getPrincipal()
						.getId());
		if (binding.hasErrors())
			result = this.createEditModelAndView(applications);
		else
			try {
				this.applicationService.save(applications);
				result = new ModelAndView(
						"redirect:/application/handyworker/list.do?="
								+ a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(applications,
						"application.commit.error");

			}
		return result;
	}

	// DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Application applications,
			final BindingResult binding) {

		ModelAndView result;
		final HandyWorker a = this.handyWorkerService
				.findHandyWorkerByUserAccount(LoginService.getPrincipal()
						.getId());
		try {
			this.applicationService.delete(applications);
			result = new ModelAndView(
					"redirect:/application/handyworker/list.do?handyWorkerId="
							+ a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(applications,
					"application.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(
			final Application application, final String message) {
		ModelAndView result;

		final int varTax = this.configurationService.findOne().getVarTax();

		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", message);
		result.addObject("varTax", varTax);
		result.addObject("isRead", false);
		result.addObject("requestURI", "application/handyworker/edit.do");

		return result;
	}
}

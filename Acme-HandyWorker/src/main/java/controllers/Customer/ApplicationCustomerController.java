package controllers.Customer;

import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.ApplicationService;
import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private CustomerService customerService;

	// Constructor---------------------------------------------------------

	public ApplicationCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Application> applications = new HashSet<Application>();
		final Customer c = this.customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		for (FixUpTask f : fixUpTaskService
				.findFixUpTaskByCustomerId(c.getId())) {
			applications.addAll(applicationService
					.findApplicationsByFixUpTeaskId(f.getId()));
		}

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/list.do");
		result.addObject("customerId", c.getId());
		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Customer c = this.customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		Application application = null;

		try {
			application = this.applicationService.findOne(applicationId);

			Assert.isTrue(application.getFixUpTask().getCustomer().equals(c));

			result = this.createAndEditModelAndView(application);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/application/customer/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message",
						"application.error.noCustomer");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(application);
		else
			try {
				final Customer c = this.customerService
						.findByUserAccount(LoginService.getPrincipal().getId());
				Assert.isTrue(application.getFixUpTask().getCustomer()
						.equals(c));
				this.applicationService.save(application);

				result = new ModelAndView(
						"redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {

				result = this.createAndEditModelAndView(application,
						"commit.error");
			}

		return result;
	}

	// METHODS
	protected ModelAndView createAndEditModelAndView(
			final Application application) {
		ModelAndView result;
		result = this.createAndEditModelAndView(application, null);
		return result;
	}

	protected ModelAndView createAndEditModelAndView(
			final Application application, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/edit");
		result.addObject("message", message);
		result.addObject(
				"requestURI",
				"application/customer/edit.do?applicationId="
						+ application.getId());
		result.addObject("application", application);
		result.addObject("isRead", false);
		
		Collection<String> statuses = new HashSet<String>();
		statuses.add("PENDING");
		statuses.add("ACCEPTED");
		statuses.add("REJECTED");
		result.addObject("statuses", statuses);

		return result;
	}
}

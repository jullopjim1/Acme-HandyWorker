
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

import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import forms.ApplicationForm;
import forms.ApplicationForm2;

@Controller
@RequestMapping("/application/customer")
public class ApplicationCustomerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private CreditCardService	creditCardService;


	// Constructor---------------------------------------------------------

	public ApplicationCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Application> applications = new HashSet<Application>();
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		for (final FixUpTask f : this.fixUpTaskService.findFixUpTaskByCustomerId(c.getId()))
			applications.addAll(this.applicationService.findApplicationsByFixUpTeaskId(f.getId()));

		result = new ModelAndView("application/list");
		result.addObject("applications", applications);
		result.addObject("requestURI", "application/customer/list.do");
		result.addObject("customerId", c.getId());
		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int applicationId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		Application application = null;
		final ApplicationForm applicationForm = new ApplicationForm();
		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(application.getFixUpTask().getCustomer().equals(c));

			applicationForm.setId(application.getId());
			applicationForm.setComments(application.getComments());

			result = this.createAndEditModelAndView(applicationForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/application/customer/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message",
						"application.error.noCustomer");
			else {
				result = this.createAndEditModelAndView(applicationForm,
						"commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ApplicationForm applicationForm,
			final BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(applicationForm,
					"commit.error");
		else
			try {
				final Customer c = this.customerService
						.findByUserAccount(LoginService.getPrincipal().getId());
				Application a = applicationService.findOne(applicationForm
						.getId());
				Assert.isTrue(a.getFixUpTask().getCustomer().equals(c));

				a.setComments(applicationForm.getComments());
				this.applicationService.save(a);

				result = new ModelAndView(
						"redirect:/application/customer/list.do");

			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(applicationForm,
						"commit.error");
			}
		return result;
	}

	// DECLINE
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView decline(final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Customer c = this.customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		Application application = null;
		ApplicationForm applicationForm = new ApplicationForm();
		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(application.getFixUpTask().getCustomer().equals(c));
			Assert.isTrue(application.getStatus().equals("PENDING"));

			applicationForm.setId(application.getId());
			applicationForm.setComments(application.getComments());

			result = this.declineModelAndView(applicationForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/application/customer/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message", "application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message",
						"application.error.noCustomer");
			else if (application.getStatus() != "PENDING") {
				redirectAttrs.addFlashAttribute("message",
						"application.error.statusNoPending");
			} else {
				result = this.declineModelAndView(applicationForm,
						"commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView decline(@Valid final ApplicationForm applicationForm,
			final BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(applicationForm, "commit.error");
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				final Application a = this.applicationService.findOne(applicationForm.getId());
				Assert.isTrue(a.getFixUpTask().getCustomer().equals(c));

				a.setStatus("REJECTED");
				this.applicationService.save(a);

				result = new ModelAndView(
						"redirect:/application/customer/list.do");

			} catch (final Throwable oops) {
				result = this.createAndEditModelAndView(applicationForm,
						"commit.error");
			}
		return result;
	}

	// ACCEPT
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(final int applicationId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Customer c = this.customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		Application application = null;
		CreditCard cc = creditCardService.create();
		ApplicationForm2 applicationForm = new ApplicationForm2();
		try {
			application = this.applicationService.findOne(applicationId);
			Assert.isTrue(application != null);
			Assert.isTrue(application.getFixUpTask().getCustomer().equals(c));
			Assert.isTrue(application.getStatus().equals("PENDING"));

			applicationForm.setApplicationId(application.getId());
			applicationForm.setBrandName(cc.getBrandName());
			applicationForm.setCVVCode(cc.getCVVCode());
			applicationForm.setExpirationMonth(cc.getExpirationMonth());
			applicationForm.setExpirationYear(cc.getExpirationYear());
			applicationForm.setHolderName(cc.getHolderName());
			applicationForm.setNumber(cc.getNumber());

			result = this.acceptModelAndView(applicationForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/application/customer/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message",
						"application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message",
						"application.error.noCustomer");
			else if (application.getStatus() != "PENDING") {
				redirectAttrs.addFlashAttribute("message",
						"application.error.statusNoPendingAccept");
			} else {
				result = this.acceptModelAndView(applicationForm,
						"commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = "save")
	public ModelAndView acceptSave(
			@Valid final ApplicationForm2 applicationForm,
			final BindingResult binding) {
		ModelAndView result = null;
		CreditCard creditCardSaved = null;

		if (binding.hasErrors()) {
			result = this.acceptModelAndView(applicationForm, "commit.error");
			System.out.println(binding.toString());
		} else
			try {
				final Customer c = this.customerService
						.findByUserAccount(LoginService.getPrincipal().getId());
				Application a = applicationService.findOne(applicationForm
						.getApplicationId());
				Assert.isTrue(a.getFixUpTask().getCustomer().equals(c));
				CreditCard cc = creditCardService.create();

				cc.setBrandName(applicationForm.getBrandName());
				cc.setCVVCode(applicationForm.getCVVCode());
				cc.setExpirationMonth(applicationForm.getExpirationMonth());
				cc.setExpirationYear(applicationForm.getExpirationYear());
				cc.setHolderName(applicationForm.getHolderName());
				cc.setNumber(applicationForm.getNumber());
				creditCardSaved = creditCardService.save(cc);

				a.setStatus("ACCEPTED");
				;
				a.setCreditCard(creditCardSaved);
				this.applicationService.save(a);

				result = new ModelAndView("redirect:/application/customer/list.do");

			} catch (final Throwable oops) {

				result = this.acceptModelAndView(applicationForm,
						"commit.error");
			}
		return result;
	}

	// METHODS
	protected ModelAndView createAndEditModelAndView(final ApplicationForm applicationForm) {
		ModelAndView result;
		result = this.createAndEditModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView createAndEditModelAndView(final ApplicationForm applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/editForm");
		result.addObject("message", message);
		result.addObject("requestURI", "application/customer/edit.do?applicationId=" + applicationForm.getId());
		result.addObject("applicationForm", applicationForm);

		return result;
	}

	protected ModelAndView acceptModelAndView(
			final ApplicationForm2 applicationForm) {
		ModelAndView result;
		result = this.acceptModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView acceptModelAndView(
			final ApplicationForm2 applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/accept");
		result.addObject("message", message);
		result.addObject("requestURI",
				"application/customer/accept.do?applicationId="
						+ applicationForm.getApplicationId());
		result.addObject("applicationForm", applicationForm);
		return result;
	}

	protected ModelAndView declineModelAndView(
			final ApplicationForm applicationForm) {
		ModelAndView result;
		result = this.declineModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView declineModelAndView(
			final ApplicationForm applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/reject");
		result.addObject("message", message);
		result.addObject("requestURI",
				"application/customer/reject.do?applicationId="
						+ applicationForm.getId());
		result.addObject("applicationForm", applicationForm);

		return result;
	}
}

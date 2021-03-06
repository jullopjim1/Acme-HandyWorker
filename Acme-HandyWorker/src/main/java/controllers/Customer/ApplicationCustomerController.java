
package controllers.Customer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
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
import services.CreditCardService;
import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import forms.ApplicationColour;
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

		final Date actualDate = new Date();
		final Collection<ApplicationColour> applicationsColour = new ArrayList<ApplicationColour>();
		for (final Application a : applications) {
			final ApplicationColour apc = new ApplicationColour();
			apc.setApplication(a);
			String color = "PENDING";
			if (a.getStatus().equals("ACCEPTED"))
				color = "ACCEPTED";
			else if (a.getStatus().equals("REJECTED"))
				color = "REJECTED";
			else if ((a.getStatus().equals("PENDING")) && (a.getFixUpTask().getDeadline().before(actualDate)))
				color = "PENDINGANDPASSED";
			apc.setColor(color);
			applicationsColour.add(apc);
		}

		result = new ModelAndView("application/list");
		result.addObject("applications", applicationsColour);
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
			Assert.notNull(application);
			Assert.isTrue(application.getFixUpTask().getCustomer().equals(c));

			applicationForm.setId(application.getId());
			applicationForm.setComments(application.getComments());

			result = this.createAndEditModelAndView(applicationForm);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/application/customer/list.do");
			if (application == null)
				redirectAttrs.addFlashAttribute("message1", "application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message1", "application.error.noCustomer");
			else
				result = this.createAndEditModelAndView(applicationForm, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ApplicationForm applicationForm, final BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(applicationForm, "commit.error");
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				final Application a = this.applicationService.findOne(applicationForm.getId());
				Assert.isTrue(a.getFixUpTask().getCustomer().equals(c));

				a.setComments(applicationForm.getComments());
				this.applicationService.save(a);

				result = new ModelAndView("redirect:/application/customer/list.do");

			} catch (final Throwable oops) {
				if (oops.getMessage().equals("errorCredit"))
					result = this.createAndEditModelAndView(applicationForm, "commit.errorCredit");
				else
					result = this.createAndEditModelAndView(applicationForm, "commit.error");

			}
		return result;
	}

	// DECLINE
	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView decline(final int applicationId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		Application application = null;
		final ApplicationForm applicationForm = new ApplicationForm();
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
				redirectAttrs.addFlashAttribute("message1", "application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message1", "application.error.noCustomer");
			else if (application.getStatus() != "PENDING")
				redirectAttrs.addFlashAttribute("message1", "application.error.statusNoPendingDecline");
			else
				result = this.declineModelAndView(applicationForm, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.POST, params = "save")
	public ModelAndView decline(@Valid final ApplicationForm applicationForm, final RedirectAttributes redirectAttrs, final BindingResult binding) {
		ModelAndView result = null;
		final Application a = this.applicationService.findOne(applicationForm.getId());
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(applicationForm, "commit.error");
		else
			try {
				Assert.isTrue(a.getFixUpTask().getCustomer().equals(c));

				a.setStatus("REJECTED");
				this.applicationService.save(a);

				result = new ModelAndView("redirect:/application/customer/list.do");

			} catch (final Throwable oops) {
				result = new ModelAndView("redirect:/application/customer/list.do");
				if (a == null)
					redirectAttrs.addFlashAttribute("message1", "application.error.unexist");
				else if (!a.getFixUpTask().getCustomer().equals(c))
					redirectAttrs.addFlashAttribute("message1", "application.error.noCustomer");
				else if (a.getStatus() != "PENDING")
					redirectAttrs.addFlashAttribute("message1", "application.error.statusNoPendingDecline");
				else
					result = this.declineModelAndView(applicationForm, "commit.error");
			}
		return result;
	}

	// ACCEPT
	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(final int applicationId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		Application application = null;
		final CreditCard cc = this.creditCardService.create();
		final ApplicationForm2 applicationForm = new ApplicationForm2();
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
				redirectAttrs.addFlashAttribute("message1", "application.error.unexist");
			else if (!application.getFixUpTask().getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message1", "application.error.noCustomer");
			else if (application.getStatus() != "PENDING")
				redirectAttrs.addFlashAttribute("message1", "application.error.statusNoPendingAccept");
			else
				result = this.acceptModelAndView(applicationForm, "commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/accept", method = RequestMethod.POST, params = "save")
	public ModelAndView acceptSave(@Valid final ApplicationForm2 applicationForm, final BindingResult binding) {
		ModelAndView result = null;
		CreditCard creditCardSaved = null;
		final Calendar cal = Calendar.getInstance();
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH) + 1;

		if (binding.hasErrors())
			result = this.acceptModelAndView(applicationForm, "application.invalidCreditCard");
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				final Application a = this.applicationService.findOne(applicationForm.getApplicationId());
				Assert.isTrue(a.getFixUpTask().getCustomer().equals(c));
				Assert.isTrue(!((applicationForm.getExpirationYear() == year) && (applicationForm.getExpirationMonth() <= month)));
				final CreditCard cc = this.creditCardService.create();

				cc.setBrandName(applicationForm.getBrandName());
				cc.setCVVCode(applicationForm.getCVVCode());
				cc.setExpirationMonth(applicationForm.getExpirationMonth());
				cc.setExpirationYear(applicationForm.getExpirationYear());
				cc.setHolderName(applicationForm.getHolderName());
				cc.setNumber(applicationForm.getNumber());
				creditCardSaved = this.creditCardService.save(cc);

				a.setStatus("ACCEPTED");
				a.setCreditCard(creditCardSaved);
				this.applicationService.save(a);

				result = new ModelAndView("redirect:/application/customer/list.do");

			} catch (final Throwable oops) {

				if (applicationForm.getExpirationYear() < year)
					result = this.acceptModelAndView(applicationForm, "commit.errorCredit");
				else if ((applicationForm.getExpirationYear() == year) && (applicationForm.getExpirationMonth() <= month))
					result = this.acceptModelAndView(applicationForm, "commit.errorCredit");
				else
					result = this.acceptModelAndView(applicationForm, "application.invalidCreditCard");
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
		result.addObject("message1", message);
		result.addObject("requestURI", "application/customer/edit.do?applicationId=" + applicationForm.getId());
		result.addObject("applicationForm", applicationForm);

		return result;
	}

	protected ModelAndView acceptModelAndView(final ApplicationForm2 applicationForm) {
		ModelAndView result;
		result = this.acceptModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView acceptModelAndView(final ApplicationForm2 applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/accept");
		result.addObject("message1", message);
		result.addObject("requestURI", "application/customer/accept.do?applicationId=" + applicationForm.getApplicationId());
		result.addObject("applicationForm", applicationForm);
		return result;
	}

	protected ModelAndView declineModelAndView(final ApplicationForm applicationForm) {
		ModelAndView result;
		result = this.declineModelAndView(applicationForm, null);
		return result;
	}

	protected ModelAndView declineModelAndView(final ApplicationForm applicationForm, final String message) {
		final ModelAndView result;

		result = new ModelAndView("application/reject");
		result.addObject("message1", message);
		result.addObject("requestURI", "application/customer/reject.do?applicationId=" + applicationForm.getId());
		result.addObject("applicationForm", applicationForm);

		return result;
	}
}

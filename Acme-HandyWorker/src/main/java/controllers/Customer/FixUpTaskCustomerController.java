
package controllers.Customer;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;
import security.LoginService;
import services.CategoryService;
import services.ComplaintService;
import services.ConfigurationService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private WarrantyService			warrantyService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private ComplaintService		complaintService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public FixUpTaskCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		fixUpTasks = this.fixUpTaskService.findFixUpTaskByCustomerId(c.getId());
		final String language = LocaleContextHolder.getLocale().getLanguage();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/customer/list.do");
		result.addObject("customerId", c.getId());
		result.addObject("lang", language.toUpperCase());
		result.addObject("complaintService", this.complaintService);

		// Ver si una fixUptask tiene complaint o no
		// TODO: Has Complaint??

		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixUpTask;
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());

		fixUpTask = this.fixUpTaskService.create(c.getId());

		result = this.createAndEditModelAndView(fixUpTask);

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int fixUpTaskId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		FixUpTask fixUpTask = null;

		try {
			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

			result = this.ShowModelAndView(fixUpTask);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			if (fixUpTask == null)
				redirectAttrs.addFlashAttribute("message", "fixUpTask.error.unexist");
		}

		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int fixUpTaskId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;

		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		FixUpTask fixUpTask = null;

		try {
			fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
			Assert.notNull(fixUpTask);
			Assert.isTrue(fixUpTask.getCustomer().equals(c));

			result = this.createAndEditModelAndView(fixUpTask);

		} catch (final Throwable e) {

			result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			if (fixUpTask == null)
				redirectAttrs.addFlashAttribute("message", "fixUpTask.error.unexist");
			else if (!fixUpTask.getCustomer().equals(c))
				redirectAttrs.addFlashAttribute("message", "fixUpTask.error.noCustomer");
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(fixUpTask, "commit.error");
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				Assert.isTrue(fixUpTask.getCustomer().equals(c));
				this.fixUpTaskService.save(fixUpTask);

				result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {
				if (fixUpTask.getDeadline().before(fixUpTask.getMoment()) && fixUpTask.getDeadline() != null)
					result = this.createAndEditModelAndView(fixUpTask, "fixuptask.error.deadlineError");
				else
					result = this.createAndEditModelAndView(fixUpTask, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createAndEditModelAndView(fixUpTask);
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				Assert.isTrue(fixUpTask.getCustomer().equals(c));
				this.fixUpTaskService.delete(fixUpTask);

				result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {

				result = this.createAndEditModelAndView(fixUpTask, "commit.error");
			}

		return result;
	}

	// AUXILIARY METHODS

	protected ModelAndView createAndEditModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;
		result = this.createAndEditModelAndView(fixUpTask, null);
		return result;
	}

	protected ModelAndView createAndEditModelAndView(final FixUpTask fixUpTask, final String message) {
		final ModelAndView result;

		final Collection<Warranty> warranties = this.warrantyService.warrantiesFinalMode();
		final Collection<Category> categories = this.categoryService.findAll();

		final int varTax = this.configurationService.findOne().getVarTax();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("message", message);
		result.addObject("requestURI", "fixUpTask/customer/edit.do");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("varTax", varTax);
		result.addObject("isRead", false);

		return result;
	}

	protected ModelAndView ShowModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;
		result = this.ShowModelAndView(fixUpTask, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final FixUpTask fixUpTask, final String message) {
		final ModelAndView result;
		final Collection<Warranty> warranties = this.warrantyService.warrantiesFinalMode();
		final Collection<Category> categories = this.categoryService.findAll();

		final int varTax = this.configurationService.findOne().getVarTax();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("message", message);
		result.addObject("requestURI", "fixUpTask/customer/show.do");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("varTax", varTax);
		result.addObject("isRead", true);

		return result;
	}
}

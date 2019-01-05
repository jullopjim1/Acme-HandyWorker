
package controllers.Customer;

import java.util.Collection;

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
import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private WarrantyService		warrantyService;

	@Autowired
	private CategoryService		categoryService;


	// Constructor---------------------------------------------------------

	public FixUpTaskCustomerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixuptasks;

		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		fixuptasks = this.fixUpTaskService.findFixUpTaskByCustomerId(c.getId());
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixuptasks);
		result.addObject("requestURI", "fixUpTask/customer/list.do");
		result.addObject("customerId", c.getId());
		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixUpTask fixUpTask;
		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());

		fixUpTask = this.fixUpTaskService.create(c.getId());

		result = this.createModelAndView(fixUpTask);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUpTask fixUpTask, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createModelAndView(fixUpTask, binding.toString());
		else
			try {
				this.fixUpTaskService.save(fixUpTask);
				result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {
				result = this.createModelAndView(fixUpTask, "commit.error");
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

			Assert.isTrue(fixUpTask.getCustomer().equals(c));

			final Collection<Warranty> warranties = this.warrantyService.findAll();
			final Collection<Category> categories = this.categoryService.findAll();

			result = new ModelAndView("fixUpTask/edit");
			result.addObject("fixUpTask", fixUpTask);
			result.addObject("warranties", warranties);
			result.addObject("categories", categories);

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
	public ModelAndView saveEdit(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(fixUpTask, "commit.error");
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				Assert.isTrue(fixUpTask.getCustomer().equals(c));
				this.fixUpTaskService.save(fixUpTask);

				result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(fixUpTask, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final FixUpTask fixUpTask, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(fixUpTask, "commit.error");
		else
			try {
				final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
				Assert.isTrue(fixUpTask.getCustomer().equals(c));
				this.fixUpTaskService.delete(fixUpTask);

				result = new ModelAndView("redirect:/fixUpTask/customer/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(fixUpTask, "commit.error");
			}

		return result;
	}

	// AUXILIARY METHODS

	protected ModelAndView createModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;
		result = this.createModelAndView(fixUpTask, null);
		return result;
	}

	protected ModelAndView createModelAndView(final FixUpTask fixUpTask, final String message) {
		ModelAndView result;

		final Collection<Warranty> warranties = this.warrantyService.findAll();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("fixUpTask/create");
		result.addObject("message", message);
		result.addObject("requestURI", "fixUpTask/customer/create.do");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);

		return result;
	}

	protected ModelAndView editModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;
		result = this.editModelAndView(fixUpTask, null);
		return result;
	}

	protected ModelAndView editModelAndView(final FixUpTask fixUpTask, final String message) {
		ModelAndView result;

		final Collection<Warranty> warranties = this.warrantyService.findAll();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("message", message);
		result.addObject("requestURI", "fixUpTask/customer/edit.do");
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);

		return result;
	}
}

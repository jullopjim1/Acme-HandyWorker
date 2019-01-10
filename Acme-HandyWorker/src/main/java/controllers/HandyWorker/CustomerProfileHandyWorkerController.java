
package controllers.HandyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import services.FinderService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Customer;
import domain.Finder;
import domain.FixUpTask;

@Controller
@RequestMapping("/handyWorker")
public class CustomerProfileHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private FinderService		finderService;


	// Constructor---------------------------------------------------------

	public CustomerProfileHandyWorkerController() {
		super();
	}

	// Show
	@RequestMapping(value = "/viewProfileCustomer", method = RequestMethod.GET)
	public ModelAndView viewProfileCustomer(@RequestParam final int customerId) {
		final ModelAndView modelAndView;

		final Customer c = this.customerService.findOne(customerId);

		modelAndView = this.createEditModelAndView(c);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "handyWorker/viewProfileCustomer.do?customerId" + customerId);
		return modelAndView;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/fixUpTasksCustomer", method = RequestMethod.GET)
	public ModelAndView fixUpTasksCustomer(@RequestParam final int customerId) {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = this.fixUpTaskService.findFixUpTaskByCustomerId(customerId);
		final String language = LocaleContextHolder.getLocale().getLanguage();
		final Finder finder = this.finderService.create();
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "handyWorker/fixUpTasksCustomer.do?customerId=" + customerId);
		result.addObject("lang", language.toUpperCase());
		result.addObject("finder", finder);

		return result;
	}

	// Methods

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Customer customer, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/showCustomer");
		result.addObject("customer", customer);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "handyWorker/viewProfileCustomer.do?customerId" + customer.getId());

		return result;
	}
}

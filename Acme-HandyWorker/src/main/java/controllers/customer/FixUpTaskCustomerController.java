
package controllers.customer;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CustomerService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/customer")
public class FixUpTaskCustomerController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CustomerService	customerService;


	//Constructor---------------------------------------------------------

	public FixUpTaskCustomerController() {
		super();
	}
	
	//List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixuptasks;

		final Customer c = this.customerService.findByUseraccount(LoginService.getPrincipal());
		System.out.println(c.getId()+ " -- " + c.getUserAccount().getUsername());
		fixuptasks = this.fixUpTaskService.findFixUpTaskByCustomerId(c.getId());
		System.out.println(fixuptasks);
		
		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixuptasks);
		result.addObject("requestURI", "fixUpTask/customer/list.do");
		return result;
	}
}

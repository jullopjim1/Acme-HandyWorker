
package controllers.Customer;

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
import services.ComplaintService;
import services.CustomerService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Customer;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private CustomerService		customerService;


	//Constructor-----------------------------------------------------

	public ComplaintCustomerController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Complaint> complaints;

		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		complaints = this.complaintService.findComplaintsByCustomerId(c.getId());

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("customerId", c.getId());
		result.addObject("requestURI", "/list.do?customerId=?" + c.getId());

		return result;

	}

	//Create (Se crea desde el listado de fixUpTask)-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		final Complaint complaint;

		final Customer c = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		complaint = this.complaintService.create(c.getId(), fixUpTaskId);
		result = this.createEditModelAndView(complaint);
		result.addObject("customerId", c.getId());
		return result;
	}

	//Edit-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId) {
		final ModelAndView result;
		Complaint complaint;

		complaint = this.complaintService.findOne(complaintId);
		Assert.notNull(complaint);
		result = this.createEditModelAndView(complaint);

		return result;
	}
	//Save-------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;
		final Customer a = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());

		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint);
		else
			try {
				this.complaintService.save(complaint);
				result = new ModelAndView("redirect:/complaint/customer/list.do?customerId=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		return result;
	}

	//Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;
		final Customer a = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		try {
			this.complaintService.delete(complaint);
			result = new ModelAndView("redirect:/list.do?customerId=" + a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(complaint, "complaint.commit.error");
		}
		return result;
	}

	//ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String message) {
		ModelAndView result;

		final Customer a = this.customerService.findByUserAccount(LoginService.getPrincipal().getId());
		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("customerId", a.getId());
		result.addObject("requestURI", "complaint/customer/edit.do");

		return result;
	}
}

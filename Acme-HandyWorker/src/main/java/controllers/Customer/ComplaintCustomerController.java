
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

import controllers.AbstractController;
import security.LoginService;
import services.ComplaintService;
import services.CustomerService;
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
		ModelAndView modelAndView;
		final Collection<Complaint> complaints = this.complaintService.findAll();

		modelAndView = new ModelAndView("complaint/list");
		modelAndView.addObject("complaints", complaints);
		modelAndView.addObject("requestURI", "/complaint/customer/list.do");

		return modelAndView;

	}

	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {
		final ModelAndView modelAndView = new ModelAndView("complaint/edit");

		final Complaint complaint = this.complaintService.findOne(complaintId);

		modelAndView.addObject("complaint", complaint);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/complaint/customer/show.do?actorId=" + complaintId);

		return modelAndView;

	}

	//Create-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int customerId, final int fixUpTaskId) {
		ModelAndView result;
		final Complaint complaint;

		complaint = this.complaintService.create(customerId, fixUpTaskId);
		result = this.createEditModelAndView(complaint);

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
		final Customer a = this.customerService.findByUseraccount(LoginService.getPrincipal());
		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint);
		else
			try {
				this.complaintService.save(complaint);
				result = new ModelAndView("redirect:complaint/customer/list.do?customerId=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint, "complaint.commit.error");
			}
		return result;
	}

	//Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Complaint complaint, final BindingResult binding) {
		ModelAndView result;
		final Customer a = this.customerService.findByUseraccount(LoginService.getPrincipal());
		try {
			this.complaintService.delete(complaint);
			result = new ModelAndView("redirect:complaint/customer/list.do?customerId=" + a.getId());
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
		Collection<Complaint> complaints;

		complaints = this.complaintService.findAll();
		final Customer a = this.customerService.findByUseraccount(LoginService.getPrincipal());
		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("administratorId", a.getId());
		result.addObject("requestURI", "complaint/customer/edit.do");

		return result;
	}
}

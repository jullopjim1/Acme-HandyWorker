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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.ComplaintService;
import services.CustomerService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Customer;

@Controller
@RequestMapping("/complaint/customer")
public class ComplaintCustomerController extends AbstractController {

	// Service---------------------------------------------------------

	@Autowired
	private ComplaintService complaintService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ReportService reportService;

	// Constructor-----------------------------------------------------

	public ComplaintCustomerController() {
		super();
	}

	// List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Complaint> complaints;

		final Customer c = this.customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		complaints = this.complaintService
				.findComplaintsByCustomerId(c.getId());

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("customerId", c.getId());
		result.addObject("reportService", this.reportService);
		result.addObject("requestURI", "/list.do?customerId=?" + c.getId());

		return result;

	}

	// Create (Se crea desde el listado de
	// fixUpTask)-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		final Complaint complaint;

		complaint = this.complaintService.create(fixUpTaskId);
		result = this.createEditModelAndView(complaint);
		return result;
	}

	// Edit-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int complaintId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Complaint complaint;
		complaint = this.complaintService.findOne(complaintId);
		Customer customer = customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		try {
			Assert.notNull(complaint);
			Assert.isTrue(complaint.getCustomer().equals(customer));
			Assert.isTrue(!complaint.getIsFinal());
			result = this.createEditModelAndView(complaint);

		} catch (Throwable e) {
			result = new ModelAndView("redirect:/complaint/customer/list.do");
			if (complaint == null)
				redirectAttrs.addFlashAttribute("message",
						"complaint.error.unexist");
			else if (!(complaint.getCustomer().equals(customer)))
				redirectAttrs.addFlashAttribute("message",
						"complaint.error.noCustomer");
			else if(complaint.getIsFinal()==true){
				redirectAttrs.addFlashAttribute("message",
						"complaint.error.isFinal");
			}
			else
				result = this.createEditModelAndView(complaint,
						"commit.error");
		}
		return result;
	}

	// Save-------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint complaint,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(complaint);
		else
			try {
				this.complaintService.save(complaint);
				result = new ModelAndView(
						"redirect:/complaint/customer/list.do?customerId="
								+ complaint.getCustomer().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(complaint,
						"complaint.commit.error");
			}
		return result;
	}

	// Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Complaint complaint,
			final BindingResult binding) {
		ModelAndView result;
		final Customer a = this.customerService.findByUserAccount(LoginService
				.getPrincipal().getId());
		try {
			this.complaintService.delete(complaint);
			result = new ModelAndView(
					"redirect:/complaint/customer/list.do?customerId="
							+ a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(complaint,
					"complaint.commit.error");
		}
		return result;
	}

	// ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("customerId", complaint.getCustomer().getId());
		result.addObject("requestURI", "complaint/customer/edit.do?fixUpTask="
				+ complaint.getFixUpTask().getId());

		return result;
	}
}

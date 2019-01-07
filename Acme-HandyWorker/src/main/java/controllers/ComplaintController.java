
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ComplaintService;
import services.ReportService;
import domain.Complaint;
import domain.Report;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private ReportService		reportService;


	//Constructor-----------------------------------------------------

	public ComplaintController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;
		final Collection<Complaint> complaints = this.complaintService.findAll();

		modelAndView = new ModelAndView("complaint/list");
		modelAndView.addObject("complaints", complaints);
		//Vemos si el report es nulo
		Boolean reportNull = false;
		for (final Complaint c : complaints) {
			final Report report = this.reportService.findReportByComplaintId(c.getId());
			if (report == null) {
				reportNull = true;
				modelAndView.addObject("complaintId", c.getId());
			}

		}
		modelAndView.addObject("reportNull", reportNull);
		modelAndView.addObject("requestURI", "complaint/list.do");

		return modelAndView;

	}
	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int complaintId) {
		final ModelAndView modelAndView;

		final Complaint complaint = this.complaintService.findOne(complaintId);

		modelAndView = this.createEditModelAndView(complaint);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/show.do?complaintId=" + complaintId);

		return modelAndView;

	}

	protected ModelAndView createEditModelAndView(final Complaint complaint) {
		ModelAndView result;

		result = this.createEditModelAndView(complaint, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String message) {
		ModelAndView result;

		result = new ModelAndView("complaint/edit");
		result.addObject("complaint", complaint);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "complaint/edit.do");

		return result;
	}
}

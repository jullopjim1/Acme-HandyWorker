
package controllers.Referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ComplaintService;
import services.RefereeService;
import services.ReportService;
import controllers.AbstractController;
import domain.Complaint;
import domain.Referee;

@Controller
@RequestMapping("/complaint/referee")
public class ComplaintRefereeController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private ReportService		reportService;


	//Constructor-----------------------------------------------------

	public ComplaintRefereeController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Complaint> complaints;

		final Referee r = this.refereeService.findByUseraccount(LoginService.getPrincipal());
		final int refereeId = r.getId();
		complaints = this.complaintService.findComplaintsByRefereeId(refereeId);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("refereeId", refereeId);
		result.addObject("reportService", this.reportService);
		result.addObject("requestURI", "complaint/referee/list.do?refereeId=?" + refereeId);

		return result;

	}
}

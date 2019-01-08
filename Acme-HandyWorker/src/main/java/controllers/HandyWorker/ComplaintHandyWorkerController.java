
package controllers.HandyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ComplaintService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Complaint;
import domain.HandyWorker;

@Controller
@RequestMapping("/complaint/handyWorker")
public class ComplaintHandyWorkerController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor-----------------------------------------------------

	public ComplaintHandyWorkerController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Complaint> complaints;

		final HandyWorker h = this.handyWorkerService.findHandyByUsername(LoginService.getPrincipal().getUsername());
		final int handyId = h.getId();
		complaints = this.complaintService.findComplaintsByHandyWorkerId(handyId);

		result = new ModelAndView("complaint/list");
		result.addObject("complaints", complaints);
		result.addObject("handyWorkerId", handyId);
		result.addObject("requestURI", "complaint/handyWorker/list.do?handyWorkerId=?" + handyId);

		return result;

	}
}

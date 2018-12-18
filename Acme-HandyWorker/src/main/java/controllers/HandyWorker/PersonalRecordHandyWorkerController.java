
package controllers.HandyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.PersonalRecord;
import security.LoginService;
import services.HandyWorkerService;
import services.PersonalRecordService;

@Controller
@RequestMapping("/personalRecord/handyworker")
public class PersonalRecordHandyWorkerController extends AbstractController {

	//Service----------------------------------------------------------------

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private PersonalRecordService	personalRecordService;


	//Constructor--------------------------------------------------------------

	public PersonalRecordHandyWorkerController() {
		super();
	}

	//show

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	private ModelAndView show(@RequestParam final int curriculumId) {
		final ModelAndView modelAndView;

		final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculumId);
		final int handyWorkerId = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId()).getId();

		modelAndView = new ModelAndView("personal/edit");
		modelAndView.addObject("personalRecord", personalRecord);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("handyWorkerId", handyWorkerId);
		modelAndView.addObject("requestURI", "/show.do?handyworkerId=" + curriculumId);

		return modelAndView;
	}

}

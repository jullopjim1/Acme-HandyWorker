
package controllers.HandyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;
import security.LoginService;
import services.CurriculumService;
import services.HandyWorkerService;
import services.PersonalRecordService;

@Controller
@RequestMapping("/personalRecord/handyworker")
public class PersonalRecordHandyWorkerController extends AbstractController {

	//Service----------------------------------------------------------------

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private PersonalRecordService	personalRecordService;


	//Constructor--------------------------------------------------------------

	public PersonalRecordHandyWorkerController() {
		super();
	}

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
		modelAndView.addObject("requestURI", "/show.do?curriculumId=" + curriculumId);

		return modelAndView;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	private ModelAndView edit(@RequestParam final int curriculumId) {
		final ModelAndView modelAndView;

		final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculumId);
		final int handyWorkerId = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId()).getId();

		modelAndView = new ModelAndView("personal/edit");
		modelAndView.addObject("personalRecord", personalRecord);
		modelAndView.addObject("isRead", false);
		modelAndView.addObject("handyWorkerId", handyWorkerId);
		modelAndView.addObject("requestURI", "/edit.do?curriculumId=" + curriculumId);

		return modelAndView;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	private ModelAndView create() {
		final ModelAndView modelAndView;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final int handyWorkerId = handyWorker.getId();
		final Curriculum curriculum = this.curriculumService.create(handyWorkerId);
		final Curriculum saved = this.curriculumService.save(curriculum);
		final PersonalRecord personalRecord = this.personalRecordService.create(saved.getId());

		modelAndView = new ModelAndView("personal/edit");
		modelAndView.addObject("personalRecord", personalRecord);
		modelAndView.addObject("isRead", false);
		modelAndView.addObject("handyWorkerId", handyWorkerId);
		modelAndView.addObject("requestURI", "/create.do?Id=" + handyWorkerId);

		return modelAndView;
	}
}

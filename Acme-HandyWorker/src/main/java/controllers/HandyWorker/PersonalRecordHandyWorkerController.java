
package controllers.HandyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
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

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	private ModelAndView create() {
		final ModelAndView modelAndView;

		final PersonalRecord personalRecord = this.personalRecordService.create();

		modelAndView = this.createEditModelAndView(personalRecord);

		return modelAndView;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	private ModelAndView show(@RequestParam final int curriculumId) {
		final ModelAndView modelAndView;

		final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculumId);

		modelAndView = this.createEditModelAndView(personalRecord);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/show.do?curriculumId=" + curriculumId);

		return modelAndView;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	private ModelAndView edit(@RequestParam final int curriculumId) {
		final ModelAndView modelAndView;

		final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculumId);
		this.personalRecordService.checkPrincipal(personalRecord);

		modelAndView = this.createEditModelAndView(personalRecord);
		modelAndView.addObject("isRead", false);
		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PersonalRecord personalRecord, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(personalRecord);
		else
			try {
				this.personalRecordService.save(personalRecord);
				result = new ModelAndView("redirect:/curriculum/handyworker/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
			}
		return result;
	}

	//Cancel
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(final PersonalRecord personalRecord) {

		ModelAndView result;

		if (personalRecord.getId() == 0)
			try {
				this.curriculumService.delete(personalRecord.getCurriculum());
				result = new ModelAndView("redirect:/curriculum/handyworker/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(personalRecord, "personalRecord.commit.error");
			}
		else
			result = new ModelAndView("redirect:/curriculum/handyworker/list.do");
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(personalRecord, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final PersonalRecord personalRecord, final String message) {
		ModelAndView result;
		final int handyWorkerId = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId()).getId();

		result = new ModelAndView("personal/edit");
		result.addObject("personalRecord", personalRecord);
		result.addObject("message", message);
		result.addObject("handyWorkerId", handyWorkerId);
		result.addObject("isRead", false);
		result.addObject("requestURI", "personalRecord/handyworker/edit.do");

		return result;
	}

}


package controllers.HandyWorker;

import java.util.ArrayList;

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
import services.CurriculumService;
import services.EducationRecordService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/educationRecord/handyworker")
public class EducationRecordHandyWorkerController extends AbstractController {

	//Service----------------------------------------------------------------------------

	@Autowired
	private EducationRecordService	educationRecordService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	//Constructor-----------------------------------------------------------------------

	public EducationRecordHandyWorkerController() {
		super();
	}

	//Listing----------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculumId) {
		ModelAndView modelAndView;

		final ArrayList<EducationRecord> educationRecords = new ArrayList<>(this.educationRecordService.findEducationRecordByCurriculumId(curriculumId));

		modelAndView = new ModelAndView("educationRecord/list");
		modelAndView.addObject("educationRecords", educationRecords);
		modelAndView.addObject("requestURI", "/educationRecord/handyworker/list.do");

		return modelAndView;
	}

	//Creating---------------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());
		final EducationRecord educationRecord = this.educationRecordService.create(curriculum.getId());

		modelAndView = this.createEditModelAndView(educationRecord);
		modelAndView.addObject("educationRecord", educationRecord);

		return modelAndView;
	}

	//Editing---------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int educationRecordId) {
		final ModelAndView modelAndView;

		final EducationRecord educationRecord = this.educationRecordService.findOne(educationRecordId);
		Assert.notNull(educationRecord);

		modelAndView = this.createEditModelAndView(educationRecord);

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EducationRecord educationRecord, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(educationRecord);
		else
			try {
				this.educationRecordService.save(educationRecord);

				modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + educationRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("DateError"))
					modelAndView = this.createEditModelAndView(educationRecord, "date.commit.error");
				else if (oops.getMessage().equals("NotPrincipal"))
					modelAndView = this.createEditModelAndView(educationRecord, "not.principal.error");
				else
					modelAndView = this.createEditModelAndView(educationRecord, "educationRecord.commit.error");
			}

		return modelAndView;
	}

	//Delete----------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ModelAndView(@RequestParam final int educationRecordId) {
		ModelAndView modelAndView = null;

		try {
			final EducationRecord educationRecord = this.educationRecordService.findOne(educationRecordId);
			this.educationRecordService.delete(educationRecord);
			modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + educationRecord.getCurriculum().getId());
		} catch (final Throwable oops) {
		}

		return modelAndView;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(educationRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EducationRecord educationRecord, final String message) {
		ModelAndView result;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());

		result = new ModelAndView("educationRecord/edit");
		result.addObject("educationRecord", educationRecord);
		result.addObject("message1", message);
		result.addObject("curriculumId", curriculum.getId());

		return result;
	}

}


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
import services.HandyWorkerService;
import services.ProfessionalRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyworker")
public class ProfessionalRecordHandyWorkerController extends AbstractController {

	//Service----------------------------------------------------------------------------

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	//Constructor-----------------------------------------------------------------------

	public ProfessionalRecordHandyWorkerController() {
		super();
	}

	//Listing----------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculumId) {
		ModelAndView modelAndView;

		final ArrayList<ProfessionalRecord> professionalRecords = new ArrayList<>(this.professionalRecordService.findProfessionalRecordByCurriculumId(curriculumId));

		modelAndView = new ModelAndView("professionalRecord/list");
		modelAndView.addObject("professionalRecords", professionalRecords);
		modelAndView.addObject("requestURI", "/professionalRecord/handyworker/list.do");

		return modelAndView;
	}

	//Creating---------------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());
		final ProfessionalRecord professionalRecord = this.professionalRecordService.create(curriculum.getId());

		modelAndView = this.createEditModelAndView(professionalRecord);
		modelAndView.addObject("professionalRecord", professionalRecord);

		return modelAndView;
	}

	//Editing---------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		final ModelAndView modelAndView;

		final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);

		modelAndView = this.createEditModelAndView(professionalRecord);

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);

				modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + professionalRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				if (oops.getMessage().equals("DateError"))
					modelAndView = this.createEditModelAndView(professionalRecord, "date.commit.error");
				else if (oops.getMessage().equals("NotPrincipal"))
					modelAndView = this.createEditModelAndView(professionalRecord, "not.principal.error");
				else
					modelAndView = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}

		return modelAndView;
	}

	//Delete----------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ModelAndView(@RequestParam final int professionalRecordId) {
		ModelAndView modelAndView = null;

		try {
			final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
			this.professionalRecordService.delete(professionalRecord);
			modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + professionalRecord.getCurriculum().getId());
		} catch (final Throwable oops) {
		}

		return modelAndView;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String message) {
		ModelAndView result;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("message1", message);
		result.addObject("curriculumId", curriculum.getId());

		return result;
	}

}

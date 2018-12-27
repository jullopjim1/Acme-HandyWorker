
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

import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;
import security.LoginService;
import services.CurriculumService;
import services.EndorserRecordService;
import services.HandyWorkerService;

@Controller
@RequestMapping("/endorserRecord/handyworker")
public class EndorserRecordHandyWorkerController extends AbstractController {

	//Service----------------------------------------------------------------------------

	@Autowired
	private EndorserRecordService	endorserRecordService;

	@Autowired
	private CurriculumService		curriculumService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	//Constructor-----------------------------------------------------------------------

	public EndorserRecordHandyWorkerController() {
		super();
	}

	//Listing----------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculumId) {
		ModelAndView modelAndView;

		final ArrayList<EndorserRecord> endorserRecords = new ArrayList<>(this.endorserRecordService.findEndorserRecordByCurriculumId(curriculumId));

		modelAndView = new ModelAndView("endorserRecord/list");
		modelAndView.addObject("endorserRecords", endorserRecords);
		modelAndView.addObject("requestURI", "/endorserRecord/handyworker/list.do");

		return modelAndView;
	}

	//Creating---------------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());
		final EndorserRecord endorserRecord = this.endorserRecordService.create(curriculum.getId());

		modelAndView = this.createEditModelAndView(endorserRecord);
		modelAndView.addObject("endorserRecord", endorserRecord);

		return modelAndView;
	}

	//Editing---------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		final ModelAndView modelAndView;

		final EndorserRecord endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(endorserRecord);

		modelAndView = this.createEditModelAndView(endorserRecord);

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);

				modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + endorserRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}

		return modelAndView;
	}

	//Delete----------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ModelAndView(@RequestParam final int endorserRecordId) {
		ModelAndView modelAndView = null;

		try {
			final EndorserRecord endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
			this.endorserRecordService.delete(endorserRecord);
			modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + endorserRecord.getCurriculum().getId());
		} catch (final Throwable oops) {
		}

		return modelAndView;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String message) {
		ModelAndView result;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("message", message);
		result.addObject("curriculumId", curriculum.getId());

		return result;
	}

}


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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/handyworker")
public class MiscellaneousRecordHandyWorkerController extends AbstractController {

	//Service----------------------------------------------------------------------------

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private CurriculumService			curriculumService;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	//Constructor-----------------------------------------------------------------------

	public MiscellaneousRecordHandyWorkerController() {
		super();
	}

	//Listing----------------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int curriculumId) {
		ModelAndView modelAndView;

		final ArrayList<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>(this.miscellaneousRecordService.findMiscellaneousRecordByCurriculumId(curriculumId));

		modelAndView = new ModelAndView("miscellaneousRecord/list");
		modelAndView.addObject("miscellaneousRecords", miscellaneousRecords);
		modelAndView.addObject("requestURI", "/miscellaneousRecord/handyworker/list.do");

		return modelAndView;
	}

	//Creating---------------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());
		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create(curriculum.getId());

		modelAndView = this.createEditModelAndView(miscellaneousRecord);
		modelAndView.addObject("miscellaneousRecord", miscellaneousRecord);

		return modelAndView;
	}

	//Editing---------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		final ModelAndView modelAndView;

		final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);

		modelAndView = this.createEditModelAndView(miscellaneousRecord);

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		if (bindingResult.hasErrors())
			modelAndView = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);

				modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + miscellaneousRecord.getCurriculum().getId());
			} catch (final Throwable oops) {
				modelAndView = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}

		return modelAndView;
	}

	//Delete----------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView ModelAndView(@RequestParam final int miscellaneousRecordId) {
		ModelAndView modelAndView = null;

		try {
			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			modelAndView = new ModelAndView("redirect:list.do?curriculumId=" + miscellaneousRecord.getCurriculum().getId());
		} catch (final Throwable oops) {
		}

		return modelAndView;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String message) {
		ModelAndView result;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorker.getId());

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("message1", message);
		result.addObject("curriculumId", curriculum.getId());

		return result;
	}

}

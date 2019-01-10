
package controllers.HandyWorker;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PhaseService;
import controllers.AbstractController;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyworker")
public class PhaseHandyWorkerController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private PhaseService	phaseService;


	//Constructor-----------------------------------------------------

	public PhaseHandyWorkerController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		final Collection<Phase> phases = this.phaseService.findPhasesByFixUpTaskIdActive(fixUpTaskId);

		result = new ModelAndView("phase/list");
		result.addObject("phases", phases);
		result.addObject("requestURI", "/phase/handyWorker/list.do");

		return result;

	}

	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int phaseId) {
		final ModelAndView result = new ModelAndView("phase/edit");

		final Phase phase = this.phaseService.findOne(phaseId);

		result.addObject("phase", phase);
		result.addObject("isRead", true);
		result.addObject("requestURI", "/phase/handyworker/show.do?phaseId=" + phaseId);

		return result;

	}

	//Create-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixUpTaskId) {
		ModelAndView result;
		final Phase phase;

		phase = this.phaseService.create(fixUpTaskId);
		result = this.createEditModelAndView(phase);

		return result;
	}

	//Edit-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phaseId) {
		final ModelAndView result;
		Phase phase;

		phase = this.phaseService.findOne(phaseId);
		Assert.notNull(phase);
		result = this.createEditModelAndView(phase);

		return result;
	}
	//Save-------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:/phase/handyworker/list.do?fixUpTaskId=" + phase.getFixUpTask().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}
		return result;
	}

	//Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		try {
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:/phase/handyworker/list.do?fixUpTaskId=" + phase.getFixUpTask().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");
		}
		return result;
	}

	//ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase, final String message) {
		ModelAndView result;

		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("fixUpTaskId", phase.getFixUpTask().getId());
		result.addObject("requestURI", "phase/handyworker/edit.do");
		return result;
	}
}

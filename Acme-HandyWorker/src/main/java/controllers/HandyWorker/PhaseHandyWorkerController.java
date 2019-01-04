
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

import security.LoginService;
import services.HandyWorkerService;
import services.PhaseService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Phase;

@Controller
@RequestMapping("/phase/handyWorker")
public class PhaseHandyWorkerController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor-----------------------------------------------------

	public PhaseHandyWorkerController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Phase> phases = this.phaseService.findAll();

		result = new ModelAndView("phase/list");
		result.addObject("phases", phases);
		result.addObject("requestURI", "/phases/handyWorker/list.do");

		return result;

	}

	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int phaseId) {
		final ModelAndView result = new ModelAndView("phase/edit");

		final Phase phase = this.phaseService.findOne(phaseId);

		result.addObject("phase", phase);
		result.addObject("isRead", true);
		result.addObject("requestURI", "/phase/handyWorker/show.do?actorId=" + phaseId);

		return result;

	}

	//Create-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Phase phase;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		phase = this.phaseService.create(a.getId());
		result = this.createEditModelAndView(phase);
		result.addObject("handyWorkerId", a.getId());

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
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:phase/handyWorker/list.do?handyWorkerId=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}
		return result;
	}

	//Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		try {
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:phase/handyWorker/list.do?administratorId=" + a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "warranty.commit.error");
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
		Collection<Phase> phases;

		phases = this.phaseService.findAll();
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("handyWorker", a.getId());
		result.addObject("requestURI", "phase/handyWorker/edit.do");

		return result;
	}
}

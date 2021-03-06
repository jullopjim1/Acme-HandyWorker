
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.LoginService;
import services.HandyWorkerService;
import services.SponsorshipService;
import services.TutorialService;
import controllers.AbstractController;
import domain.HandyWorker;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial/handyworker")
public class TutorialHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor---------------------------------------------------------

	public TutorialHandyWorkerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		tutorials = this.tutorialService.findTutorialsByHandyWorkerId(a.getId());

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "/list.do?handyWorkerId=" + a.getId());
		result.addObject("handyWorkerId", a.getId());
		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tutorial tutorial;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		tutorial = this.tutorialService.create(a.getId());
		result = this.createEditModelAndView(tutorial);
		result.addObject("handyWorkerId", a.getId());

		return result;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int tutorialId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Tutorial tutorial;
		tutorial = this.tutorialService.findOne(tutorialId);
		final HandyWorker h = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		try {
			Assert.notNull(tutorial);
			Assert.isTrue(tutorial.getHandyWorker().equals(h));
			result = this.createEditModelAndView(tutorial);
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/tutorial/handyworker/list.do");
			if (tutorial == null)
				redirectAttrs.addFlashAttribute("message1", "tutorial.error.unexist");
			else if (!tutorial.getHandyWorker().equals(h))
				redirectAttrs.addFlashAttribute("message1", "tutorial.error.notActual");
		}
		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(tutorial, "message.commit.error");
		else
			try {
				final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
				this.tutorialService.save(tutorial);
				result = new ModelAndView("redirect:/tutorial/handyworker/list.do?=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(tutorial, "tutorial.commit.error");

			}
		return result;
	}

	// DELETE
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Tutorial tutorial, final BindingResult binding) {

		ModelAndView result;
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		try {
			this.tutorialService.delete(tutorial);
			result = new ModelAndView("redirect:list.do?handyWorkerId=" + a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(tutorial, "tutorial.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String message) {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findAll();
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message1", message);
		result.addObject("isRead", false);
		result.addObject("handyWorkerId", a.getId());
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "tutorial/handyworker/edit.do");

		return result;
	}
}

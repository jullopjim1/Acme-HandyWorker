package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.HandyWorkerService;
import services.SponsorshipService;
import services.TutorialService;
import domain.HandyWorker;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private SponsorshipService sponsorshipService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Constructor---------------------------------------------------------

	public TutorialController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialService.findAll();

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");

		return result;
	}

	// Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
		try {
			Assert.notNull(tutorial);
			modelAndView = this.createEditModelAndView(tutorial);
			modelAndView.addObject("isRead", true);
			modelAndView.addObject("requestURI", "/show.do?tutorialId="
					+ tutorialId);
		} catch (Throwable e) {
			modelAndView = new ModelAndView("redirect:list.do");

			if (tutorial == null)
				redirectAttrs.addFlashAttribute("message",
						"tutorial.error.unexist");
		}
		return modelAndView;
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int handyWorkerId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Collection<Tutorial> tutorials;
		HandyWorker h = handyWorkerService.findOne(handyWorkerId);

		try {
			Assert.notNull(h);
			tutorials = this.tutorialService
					.findTutorialsByHandyWorkerId(handyWorkerId);

			result = new ModelAndView("tutorial/list");
			result.addObject("tutorials", tutorials);
			result.addObject("requestURI", "/view.do?handyWorkerId="
					+ handyWorkerId);
			result.addObject("handyWorkerId", handyWorkerId);

		} catch (Throwable e) {
			result = new ModelAndView("redirect:list.do");

			if (h == null)
				redirectAttrs.addFlashAttribute("message",
						"tutorial.error.unexisthandy");
		}
		return result;
	}

	// CreateModelAndView

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial,
			final String message) {
		ModelAndView result;

		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findAll();

		final HandyWorker a = tutorial.getHandyWorker();
		final int b = a.getId();

		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "tutorial/handyworker/edit.do");
		result.addObject("sponsorships", sponsorships);
		result.addObject("handyWorkerId", b);

		return result;
	}

}

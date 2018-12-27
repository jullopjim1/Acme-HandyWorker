
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import services.TutorialService;
import domain.Sponsorship;
import domain.Tutorial;

@Controller
@RequestMapping("/tutorial")
public class TutorialController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;


	//Constructor---------------------------------------------------------

	public TutorialController() {
		super();
	}
	//List ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialService.findAll();

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/list.do");
		result.addObject("handyWorkerId", handyWorkerId);

		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int tutorialId) {
		final ModelAndView modelAndView;

		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		modelAndView = this.createEditModelAndView(tutorial);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/show.do?tutorialId=" + tutorialId);

		return modelAndView;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Tutorial tutorial) {
		ModelAndView result;

		result = this.createEditModelAndView(tutorial, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Tutorial tutorial, final String message) {
		ModelAndView result;

		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findAll();

		result = new ModelAndView("tutorial/edit");
		result.addObject("tutorial", tutorial);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "tutorial/handyworker/edit.do");
		result.addObject("sponsorships", sponsorships);

		return result;
	}

}

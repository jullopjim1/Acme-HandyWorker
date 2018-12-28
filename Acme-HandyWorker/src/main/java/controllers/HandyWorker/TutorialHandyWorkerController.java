
package controllers.HandyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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

	//Services-----------------------------------------------------------

	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor---------------------------------------------------------

	public TutorialHandyWorkerController() {
		super();
	}
	//List ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tutorial> tutorials;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		tutorials = this.tutorialService.findTutorialsByHandyWorkerId(a.getId());

		result = new ModelAndView("tutorial/list");
		result.addObject("tutorials", tutorials);
		result.addObject("requestURI", "tutorial/handyworker/list.do");
		result.addObject("handyWorkerId", a.getId());
		return result;
	}

	//Create
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
	/*
	 * @RequestMapping(value = "/edit", method = RequestMethod.GET)
	 * public ModelAndView edit(@RequestParam final int sectionId) {
	 * ModelAndView result;
	 * Section section;
	 * 
	 * section = this.sectionService.findOne(sectionId);
	 * Assert.notNull(section);
	 * result = this.createEditModelAndView(section);
	 * 
	 * return result;
	 * }
	 * 
	 * //Save
	 * 
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	 * public ModelAndView save(@Valid final Section section, final BindingResult binding) {
	 * 
	 * ModelAndView result;
	 * 
	 * if (binding.hasErrors())
	 * result = this.createEditModelAndView(section);
	 * else
	 * try {
	 * this.sectionService.save(section);
	 * result = new ModelAndView("redirect:/section/list.do?tutorialId=" + section.getTutorial().getId());
	 * } catch (final Throwable oops) {
	 * if (oops.getMessage().equals("positionerror"))
	 * result = this.createEditModelAndView(section, "position.error");
	 * else
	 * result = this.createEditModelAndView(section, "section.commit.error");
	 * 
	 * }
	 * return result;
	 * }
	 * 
	 * //Delete
	 * 
	 * @RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	 * public ModelAndView delete(@Valid final Section section, final BindingResult binding) {
	 * 
	 * ModelAndView result;
	 * 
	 * try {
	 * this.sectionService.delete(section);
	 * result = new ModelAndView("redirect:/section/list.do?tutorialId=" + section.getTutorial().getId());
	 * } catch (final Throwable oops) {
	 * result = this.createEditModelAndView(section, "section.commit.error");
	 * }
	 * return result;
	 * }
	 */
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
		result.addObject("handyWorkerId", tutorial.getHandyWorker().getId());
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "tutorial/handyworker/edit.do");

		return result;
	}
}

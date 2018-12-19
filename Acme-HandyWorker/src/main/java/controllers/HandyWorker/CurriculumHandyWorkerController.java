
package controllers.HandyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Curriculum;
import domain.HandyWorker;
import security.LoginService;
import services.CurriculumService;
import services.HandyWorkerService;

@Controller
@RequestMapping("/curriculum/handyworker")
public class CurriculumHandyWorkerController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private CurriculumService	curriculumService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	//Constructor---------------------------------------------------------


	public CurriculumHandyWorkerController() {
		super();
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	private ModelAndView list() {
		final ModelAndView modelAndView;

		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final int handyWorkerId = handyWorker.getId();
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);

		modelAndView = new ModelAndView("curriculum/list");
		modelAndView.addObject("curriculum", curriculum);
		modelAndView.addObject("hasCurriculum", this.curriculumService.hasCurriculum(handyWorker));
		modelAndView.addObject("requestURI", "/list.do?handyworkerId=" + handyWorkerId);

		return modelAndView;
	}

	//Delete
	@RequestMapping(value = "/delete")
	public ModelAndView delete(@RequestParam final int curriculumId) {

		ModelAndView result = null;

		try {
			final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
		}
		return result;
	}

}

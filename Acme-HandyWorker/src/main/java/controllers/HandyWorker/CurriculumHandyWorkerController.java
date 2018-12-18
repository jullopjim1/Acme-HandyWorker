
package controllers.HandyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Curriculum;
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

		final int handyWorkerId = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId()).getId();
		final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);

		modelAndView = new ModelAndView("curriculum/list");
		modelAndView.addObject("curriculum", curriculum);
		modelAndView.addObject("requestURI", "/list.do?handyworkerId=" + handyWorkerId);

		return modelAndView;
	}

}

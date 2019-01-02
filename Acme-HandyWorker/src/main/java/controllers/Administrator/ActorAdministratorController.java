
package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import services.ActorService;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	//Services-------------------------------------------------------

	@Autowired
	private ActorService actorService;


	//Constructor----------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	//List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView modelAndView;

		final Collection<Actor> supicousActors = this.actorService.findSuspiciuosActors();

		modelAndView = new ModelAndView("actor/list");
		modelAndView.addObject("actors", supicousActors);
		modelAndView.addObject("requestURI", "/actor/administrator/list.do");

		return modelAndView;

	}

}

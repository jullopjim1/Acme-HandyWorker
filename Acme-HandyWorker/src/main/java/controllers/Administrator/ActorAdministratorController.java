
package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.EndorserService;
import controllers.AbstractController;
import domain.Actor;
import domain.Endorser;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	// Service---------------------------------------------------------

	@Autowired
	private ActorService	actorService;

	@Autowired
	private EndorserService	endorserService;


	// Constructor-----------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	// List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;

		final Collection<Actor> actors = this.actorService.findSuspiciousActors();

		modelAndView = new ModelAndView("actor/list");
		modelAndView.addObject("actors", actors);
		modelAndView.addObject("requestURI", "/actor/administrator/list.do");

		return modelAndView;

	}

	// Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int actorId, final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView = new ModelAndView("actor/edit");

		final Actor actor = this.actorService.findOne(actorId);
		final Endorser endorser = this.endorserService.findOne(actorId);

		try {
			Assert.notNull(actor);
			Double score = null;
			if (endorser != null)
				score = endorser.getScore();

			modelAndView.addObject("actor", actor);
			modelAndView.addObject("isRead", true);
			modelAndView.addObject("isProfile", true);
			modelAndView.addObject("score", score);
			modelAndView.addObject("requestURI", "/actor/administrator/show.do?actorId=" + actorId);
		} catch (final Throwable e) {
			modelAndView = new ModelAndView("redirect:list.do");

			if (actor == null)
				redirectAttrs.addFlashAttribute("message1", "actor.error.unexist");
		}
		return modelAndView;

	}

	// Ban-------------------------------------------------------------

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId, final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView = new ModelAndView();

		final Actor actor = this.actorService.findOne(actorId);
		try {
			Assert.notNull(actor);
			Assert.isTrue((actor.getIsBanned() == false) && (actor.getIsSuspicious() == true));
			this.actorService.ban(actor);
			modelAndView = new ModelAndView("redirect:list.do");
		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:list.do");

			if (actor == null)
				redirectAttrs.addFlashAttribute("message1", "actor.error.unexist");
			else if (!((actor.getIsBanned() == false) && (actor.getIsSuspicious() == true)))
				redirectAttrs.addFlashAttribute("message1", "actor.error.toBan");
		}

		return modelAndView;

	}

	// UnBan-------------------------------------------------------------

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam final int actorId, final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView = null;

		final Actor actor = this.actorService.findOne(actorId);
		try {
			Assert.notNull(actor);
			Assert.isTrue((actor.getIsBanned() == true) && (actor.getIsSuspicious() == true));
			this.actorService.unban(actor);
			modelAndView = new ModelAndView("redirect:list.do");

		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:list.do");

			if (actor == null)
				redirectAttrs.addFlashAttribute("message1", "actor.error.unexist");
			else if (!((actor.getIsBanned() == true) && (actor.getIsSuspicious() == true)))
				redirectAttrs.addFlashAttribute("message1", "actor.error.toUnban");
		}

		return modelAndView;

	}
}

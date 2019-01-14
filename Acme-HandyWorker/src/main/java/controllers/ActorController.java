/*
 * ProfileController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

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
import services.ActorService;
import services.EndorserService;
import services.TutorialService;
import domain.Actor;
import domain.Endorser;
import domain.Tutorial;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	private ActorService actorService;

	@Autowired
	private TutorialService tutorialService;

	@Autowired
	private EndorserService endorserService;

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		final Actor a = this.actorService.findByUserAccount(LoginService
				.getPrincipal());
		Assert.notNull(a);

		result = this.createEditModelAndView(a);

		return result;
	}

	// Show
	@RequestMapping(value = "/showProfileTutorial", method = RequestMethod.GET)
	public ModelAndView showByTutorial(@RequestParam final int tutorialId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		try {
			Assert.notNull(tutorial);
			final Actor actor1 = this.actorService.findByUserAccount(tutorial
					.getHandyWorker().getUserAccount());
			Assert.notNull(actor1);
			final int handyWorkerId = actor1.getId();
			modelAndView = this.createEditModelAndView(actor1);
			modelAndView.addObject("isRead", true);
			modelAndView.addObject("handyWorkerId", handyWorkerId);
			modelAndView.addObject(
					"requestURI",
					"/actor/showProfileTutorial.do?tutorialId="
							+ tutorial.getId());
			final Endorser e = this.endorserService.findOne(actor1.getId());
			modelAndView.addObject("score", e.getScore());

		} catch (Throwable e) {
			modelAndView = new ModelAndView("redirect:/tutorial/list.do");
			if (tutorial == null) {
				redirectAttrs.addFlashAttribute("message",
						"tutorial.error.unexisthandy");
			}
		}
		return modelAndView;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor,
			final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.actorService.update(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor,
						"actor.commit.error");

			}
		return result;
	}

	// CreateModelAndView

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor actor,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "actor/edit.do");

		return result;
	}

}

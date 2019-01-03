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

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
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
	private ActorService	actorService;

	@Autowired
	private EndorserService	endorserService;

	@Autowired
	private TutorialService	tutorialService;


	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Actor actor;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		actor = this.actorService.findOne(a.getId());
		Assert.notNull(actor);

		result = this.createEditModelAndView(actor);

		return result;
	}

	//Show
	@RequestMapping(value = "/showProfileTutorial", method = RequestMethod.GET)
	public ModelAndView showByTutorial(@RequestParam final int tutorialId) {
		final ModelAndView modelAndView;
		final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

		final Actor actor1 = this.actorService.findByUserAccount(tutorial.getHandyWorker().getUserAccount());

		final Actor actor = this.actorService.findOne(actor1.getId());
		final ArrayList<String> t = new ArrayList<>();
		t.add(Authority.HANDY);
		t.add(Authority.CUSTOMER);

		modelAndView = this.createEditModelAndView(actor);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/actor/showProfileTutorial.do?tutorialId=" + tutorial.getId());
		if (actor.getUserAccount().getAuthorities().containsAll(t)) {
			final Endorser e = this.endorserService.findEndorserByUseraccount(actor1.getUserAccount());
			modelAndView.addObject("score", e.getScore());
		}

		return modelAndView;
	}
	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				//	this.actorService.save(actor);
				result = new ModelAndView("redirect:/tutorial/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "actor.commit.error");

			}
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "actor/edit.do");

		return result;
	}

}

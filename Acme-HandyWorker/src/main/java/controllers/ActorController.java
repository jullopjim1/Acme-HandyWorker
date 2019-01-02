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
import services.EndorsementService;
import services.HandyWorkerService;
import domain.Actor;
import domain.Endorsement;
import domain.HandyWorker;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	@Autowired
	ActorService		actorService;

	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	EndorsementService	endorsementService;


	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int ActorId) {
		ModelAndView result;
		Actor actor;

		actor = this.actorService.findOne(ActorId);
		Assert.notNull(actor);

		result = this.createEditModelAndView(actor);

		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show() {
		final ModelAndView modelAndView;

		final Actor actor1 = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Actor actor = this.actorService.findOne(actor1.getId());
		final ArrayList<String> t = new ArrayList<>();
		t.add(Authority.HANDY);
		t.add(Authority.CUSTOMER);

		modelAndView = this.createEditModelAndView(actor);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/show.do?handyWorkerId=" + actor.getId());
		if (actor.getUserAccount().getAuthorities().containsAll(t)) {
			final Endorsement e = this.endorsementService.endorsementByUserAccount(actor1.getId());
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
				result = new ModelAndView("redirect:tutorial/list.do");
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

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final int b = a.getId();

		result = new ModelAndView("actor/edit");
		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "actor/edit.do");
		result.addObject("handyWorkerId", b);

		return result;
	}

}

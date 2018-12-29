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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.HandyWorkerService;
import services.ProfileService;
import domain.HandyWorker;
import domain.Profile;

@Controller
@RequestMapping("/actor")
public class ProfileController extends AbstractController {

	@Autowired
	ProfileService		profileService;

	@Autowired
	HandyWorkerService	handyWorkerService;


	// Edit ---------------------------------------------------------------		

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int profileId) {
		ModelAndView result;
		Profile profile;

		profile = this.profileService.findOne(profileId);
		Assert.notNull(profile);

		result = this.createEditModelAndView(profile);

		return result;
	}

	//Show
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int handyWorkerId) {
		final ModelAndView modelAndView;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Profile profile = this.profileService.findOne(a.getId());

		modelAndView = this.createEditModelAndView(profile);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/show.do?handyWorkerId=" + handyWorkerId);

		return modelAndView;
	}
	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Profile profile) {
		ModelAndView result;

		result = this.createEditModelAndView(profile, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Profile profile, final String message) {
		ModelAndView result;

		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final int b = a.getId();

		result = new ModelAndView("actor/edit");
		result.addObject("profile", profile);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "actor/edit.do");
		result.addObject("handyWorkerId", b);

		return result;
	}

}

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

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ProfileService;
import domain.Actor;
import domain.Profile;

@Controller
@RequestMapping("/profile")
public class ProfileController extends AbstractController {

	@Autowired
	private ProfileService	profileService;

	@Autowired
	private ActorService	actorService;


	//List------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;

		final Collection<Profile> profiles = this.profileService.findAll();

		modelAndView = new ModelAndView("profile/list");
		modelAndView.addObject("profiles", profiles);
		modelAndView.addObject("requestURI", "/profile/list.do");

		return modelAndView;

	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Profile profile;
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		profile = this.profileService.create(a.getId());
		result = this.createEditModelAndView(profile);

		return result;
	}

	//Show------------------------------------------------------------

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int profileId) {
		final ModelAndView modelAndView = new ModelAndView("profile/edit");

		final Profile profile = this.profileService.findOne(profileId);

		modelAndView.addObject("profile", profile);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/profile/show.do?profileId=" + profileId);

		return modelAndView;

	}

	// Edit ---------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int profileId) {
		final ModelAndView result;
		Profile profile;
		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		profile = this.profileService.findOne(profileId);
		Assert.notNull(profile);
		result = this.createEditModelAndView(profile);
		result.addObject("actorId", a.getId());
		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Profile profile, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(profile);
		else
			try {
				this.profileService.save(profile);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(profile, "profile.commit.error");

			}
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Profile profile) {
		ModelAndView result;

		result = this.createEditModelAndView(profile, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Profile profile, final String message) {
		final ModelAndView result;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("profile/edit");
		result.addObject("profile", profile);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("username", a.getUserAccount().getUsername());
		result.addObject("requestURI", "profile/edit.do");

		return result;
	}

}

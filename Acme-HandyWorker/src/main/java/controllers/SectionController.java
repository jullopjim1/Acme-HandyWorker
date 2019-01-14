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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.SectionService;
import services.TutorialService;
import domain.Section;
import domain.Tutorial;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private SectionService sectionService;

	@Autowired
	private TutorialService tutorialService;

	// Constructor---------------------------------------------------------

	public SectionController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Collection<Section> sections;
		final Tutorial t = this.tutorialService.findOne(tutorialId);
		try {
			Assert.notNull(t);
			sections = this.sectionService.findSectionByTutorialId(tutorialId);
			result = new ModelAndView("section/list");
			result.addObject("sections", sections);
			result.addObject("requestURI", "section/list.do");
			result.addObject("handyWorkerId", t.getHandyWorker().getId());
		} catch (Throwable e) {
			result = new ModelAndView("redirect:/tutorial/list.do");
			if (t == null)
				redirectAttrs.addFlashAttribute("message",
						"tutorial.error.unexist");
		}
		return result;
	}

}

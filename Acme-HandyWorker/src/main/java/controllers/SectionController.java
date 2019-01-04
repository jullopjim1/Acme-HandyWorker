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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.HandyWorkerService;
import services.SectionService;
import domain.HandyWorker;
import domain.Section;

@Controller
@RequestMapping("/section")
public class SectionController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private SectionService		sectionService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Constructor---------------------------------------------------------

	public SectionController() {
		super();
	}
	//List ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int tutorialId) {
		ModelAndView result;
		Collection<Section> sections;

		sections = this.sectionService.findSectionByTutorialId(tutorialId);
		final HandyWorker a = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());

		result = new ModelAndView("section/list");
		result.addObject("sections", sections);
		result.addObject("requestURI", "section/list.do");
		result.addObject("handyWorkerId", a.getId());
		return result;
	}

}

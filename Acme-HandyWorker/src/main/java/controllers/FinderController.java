/*
 * CustomerController.java
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CategoryService;
import services.FinderService;
import services.HandyWorkerService;
import services.WarrantyService;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

@Controller
@RequestMapping("/finder/handy")
public class FinderController extends AbstractController {

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private WarrantyService		warrantyService;


	// Constructors -----------------------------------------------------------

	public FinderController() {
		super();
	}

	// Update finder ---------------------------------------------------------------		

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public ModelAndView updateFinder() {
		ModelAndView result;

		final Finder finder = this.findFinder();

		final Collection<Category> categories = this.categoryService.findAll();
		final Collection<Warranty> warranties = this.warrantyService.findAll();

		result = new ModelAndView("finder/handy/update");
		result.addObject("finder", finder);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);

		return result;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		System.out.println("\n\n\n\n=======\n\n\n" + binding + "\n\n\n=======\n\n\n\n");
		if (!binding.hasErrors()) {

			this.finderService.save(finder);
			result = new ModelAndView("redirect:listFixUpTasks.do");
		} else {
			final Collection<Category> categories = this.categoryService.findAll();
			final Collection<Warranty> warranties = this.warrantyService.findAll();

			result = new ModelAndView("finder/handy/update");
			result.addObject("finder", finder);
			result.addObject("categories", categories);
			result.addObject("warranties", warranties);
		}
		return result;
	}

	// List result finder ---------------------------------------------------------------		

	@RequestMapping("/listFixUpTasks")
	public ModelAndView listFixUpTasks() {
		ModelAndView result;
		Finder finder = this.findFinder();
		//Comprobar fecha ultima actualización
		finder = this.finderService.updateFinder(finder);
		//Obtener resultados fixuptasks de finder
		final Collection<FixUpTask> fixUpTasks = finder.getFixUpTasks();

		result = new ModelAndView("finder/handy/listFixUpTasks");
		result.addObject("fixUpTasks", fixUpTasks);

		return result;
	}

	private Finder findFinder() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(userAccount.getId());

		Finder finder = this.finderService.findFinderByHandyWorkerId(handyWorker.getId());

		if (finder == null)
			finder = this.finderService.create();

		return finder;

	}
}

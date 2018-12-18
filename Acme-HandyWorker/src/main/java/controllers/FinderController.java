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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.CategoryService;
import services.FinderService;
import services.HandyWorkerService;
import services.WarrantyService;
import domain.Category;
import domain.Finder;
import domain.HandyWorker;
import domain.Warranty;

@Controller
@RequestMapping("/finder")
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

	@RequestMapping("/updateFinder")
	public ModelAndView updateFinder() {
		ModelAndView result;
		final UserAccount userAccount = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(userAccount.getId());

		Finder finder = this.finderService.findFinderByHandyWorkerId(handyWorker.getId());

		if (finder == null)
			finder = this.finderService.create();

		final Collection<Category> categories = this.categoryService.findAll();
		final Collection<Warranty> warranties = this.warrantyService.findAll();

		result = new ModelAndView("finder/handyWorker/edit");
		result.addObject("finder", finder);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);

		return result;
	}

	// List result finder ---------------------------------------------------------------		

	@RequestMapping("/listFixUpTasks")
	public ModelAndView listFixUpTasks() {
		ModelAndView result;

		result = new ModelAndView("customer/action-2");

		return result;
	}
}

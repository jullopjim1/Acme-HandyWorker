package controllers.Administrator;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CategoryService;
import controllers.AbstractController;
import domain.Category;
import forms.CategoryForm;

@Controller
@RequestMapping("/category/administrator")
public class CategoryAdministratorController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private CategoryService categoryService;

	// Constructor---------------------------------------------------------

	public CategoryAdministratorController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Category> categories;
		final String lang = LocaleContextHolder.getLocale().getLanguage()
				.toUpperCase();

		categories = categoryService.findAll();
		result = new ModelAndView("category/list");

		result.addObject("lang", lang);
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");
		return result;
	}

	// CREATE
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		CategoryForm categoryForm = new CategoryForm();

		result = this.createModelAndView(categoryForm);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CategoryForm categoryForm,
			final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createModelAndView(categoryForm, "commit.error");
		else
			try {
				Category category = categoryService.create();
				Map<String, String> map = new HashMap<String, String>();
				map.put("EN", categoryForm.getNameEN());
				map.put("ES", categoryForm.getNameES());
				category.setName(map);
				category.setRootcategory(categoryForm.getRootcategory());

				this.categoryService.save(category);
				result = new ModelAndView(
						"redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(categoryForm, "commit.error");
			}
		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int categoryId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Category category = null;
		CategoryForm categoryForm = new CategoryForm();

		try {
			category = this.categoryService.findOne(categoryId);
			Assert.isTrue(category != null);
			Assert.isTrue(categoryId != categoryService.findRootCategory()
					.getId());
			final Collection<Category> categories = this.categoryService
					.findAll();
			if (category != null) {
				categories.remove(category);
			}

			categoryForm.setId(category.getId());
			categoryForm.setNameEN(category.getName().get("EN"));
			categoryForm.setNameES(category.getName().get("ES"));
			categoryForm.setRootcategory(category.getRootcategory());

			result = new ModelAndView("category/edit");
			result.addObject("categoryForm", categoryForm);
			result.addObject("categories", categories);

		} catch (final Throwable e) {

			result = new ModelAndView(
					"redirect:/category/administrator/list.do");
			if (category == null) {
				redirectAttrs.addFlashAttribute("message",
						"category.error.unexist");
			} else if (categoryId == categoryService.findRootCategory().getId()) {
				redirectAttrs.addFlashAttribute("message",
						"category.error.rootCategoryDelete");
			}
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final CategoryForm categoryForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(categoryForm, "commit.error");
		else
			try {
				Category category = categoryService.findOne(categoryForm
						.getId());
				Map<String, String> map = new HashMap<String, String>();
				map.put("EN", categoryForm.getNameEN());
				map.put("ES", categoryForm.getNameES());
				category.setName(map);
				category.setRootcategory(categoryForm.getRootcategory());

				this.categoryService.save(category);
				result = new ModelAndView(
						"redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(categoryForm, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final CategoryForm categoryForm,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(categoryForm, "commit.error");
		else
			try {
				Category category = categoryService.findOne(categoryForm
						.getId());
				this.categoryService.delete(category);

				result = new ModelAndView(
						"redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(categoryForm, "commit.error");
			}

		return result;
	}

	// AUXILIARY METHODS

	protected ModelAndView createModelAndView(final CategoryForm categoryForm) {
		ModelAndView result;
		result = this.createModelAndView(categoryForm, null);
		return result;
	}

	protected ModelAndView createModelAndView(final CategoryForm categoryForm,
			final String message) {
		ModelAndView result;

		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("category/create");
		result.addObject("message", message);
		result.addObject("requestURI", "category/administrator/create.do");
		result.addObject("categoryForm", categoryForm);
		result.addObject("categories", categories);

		return result;
	}

	protected ModelAndView editModelAndView(final CategoryForm categoryForm) {
		ModelAndView result;
		result = this.editModelAndView(categoryForm, null);
		return result;
	}

	protected ModelAndView editModelAndView(final CategoryForm categoryForm,
			final String message) {
		ModelAndView result;

		final Collection<Category> categories = this.categoryService.findAll();
		Category category = categoryService.findOne(categoryForm.getId());
		if (category != null) {
			categories.remove(category);
		}

		result = new ModelAndView("category/edit");
		result.addObject("message", message);
		result.addObject("requestURI", "category/administrator/edit.do");
		result.addObject("categoryForm", categoryForm);
		result.addObject("categories", categories);

		return result;
	}

}
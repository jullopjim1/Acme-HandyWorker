package controllers.Administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

		categories = categoryService.findAll();
		result = new ModelAndView("category/list");
		result.addObject("categories", categories);
		result.addObject("requestURI", "category/administrator/list.do");
		return result;
	}

	// EDIT

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(final int categoryId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Category category = null;

		try {
			category = this.categoryService.findOne(categoryId);
			Assert.isTrue(category !=null);
			Assert.isTrue(categoryId != categoryService.findRootCategory().getId());
			final Collection<Category> categories = this.categoryService
					.findAll();
			if (category != null) {
				categories.remove(category);
			}
			result = new ModelAndView("category/edit");
			result.addObject("category", category);
			result.addObject("categories", categories);

		} catch (final Throwable e) {

			result = new ModelAndView(
					"redirect:/category/administrator/list.do");
			if (category == null){
				redirectAttrs.addFlashAttribute("message", "category.error.unexist");
			} else if(categoryId != categoryService.findRootCategory().getId()){
			redirectAttrs.addFlashAttribute("message",
					"category.error.rootCategoryDelete");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Category category,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(category, "commit.error");
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView(
						"redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(category, "commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView deleteEdit(@Valid final Category category,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.editModelAndView(category, "commit.error");
		else
			try {
				this.categoryService.delete(category);

				result = new ModelAndView(
						"redirect:/category/administrator/list.do");
			} catch (final Throwable oops) {

				result = this.editModelAndView(category, "commit.error");
			}

		return result;
	}
	
	// AUXILIARY METHODS

		protected ModelAndView editModelAndView(final Category category) {
			ModelAndView result;
			result = this.editModelAndView(category, null);
			return result;
		}

		protected ModelAndView editModelAndView(final Category category,
				final String message) {
			ModelAndView result;

			final Collection<Category> categories = this.categoryService.findAll();
			if(category != null){
				categories.remove(category);
			}
			
			result = new ModelAndView("category/edit");
			result.addObject("message", message);
			result.addObject("requestURI", "category/administrator/edit.do");
			result.addObject("category", category);
			result.addObject("categories", categories);

			return result;
		}

}
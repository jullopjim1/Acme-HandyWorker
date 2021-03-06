
package controllers.HandyWorker;

import java.util.ArrayList;
import java.util.Collection;

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

import security.LoginService;
import services.ApplicationService;
import services.CategoryService;
import services.ConfigurationService;
import services.FinderService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import services.WarrantyService;
import controllers.AbstractController;
import domain.Category;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Warranty;

@Controller
@RequestMapping("/fixUpTask/handyWorker")
public class FixUpTaskHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ApplicationService		applicationService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private WarrantyService			warrantyService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor---------------------------------------------------------

	public FixUpTaskHandyWorkerController() {
		super();
	}

	// List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixUpTask> fixUpTasks;

		final HandyWorker h = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final int handyWorkerId = h.getId();

		Collection<Category> categories;
		categories = this.categoryService.findAll();
		final Collection<Warranty> warranties = this.warrantyService.warrantiesFinalMode();
		final String lang = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
		final Collection<String> nameCategories = new ArrayList<>();
		for (final Category category : categories)
			nameCategories.add(category.getName().get(lang));

		fixUpTasks = this.fixUpTaskService.findAll();
		final String language = LocaleContextHolder.getLocale().getLanguage();

		final Finder finder = this.finderService.create();

		result = new ModelAndView("fixUpTask/list");
		result.addObject("fixUpTasks", fixUpTasks);
		result.addObject("requestURI", "fixUpTask/handyWorker/list.do");
		result.addObject("lang", language.toUpperCase());
		result.addObject("handyId", handyWorkerId);
		result.addObject("applicationService", this.applicationService);
		result.addObject("categories", nameCategories);
		result.addObject("warranties", warranties);
		result.addObject("finder", finder);

		return result;
	}

	@RequestMapping(value = "/addFilter", method = RequestMethod.POST)
	public ModelAndView updateFinder(@Valid final Finder finder, final BindingResult binding) {
		ModelAndView result;

		if (!binding.hasErrors())
			result = new ModelAndView("redirect:list.do");
		else {
			final Collection<FixUpTask> fixUpTasks = this.finderService.searchFixUpTask(finder, 100);

			result = this.list();
			result.getModel().remove("fixUpTasks");
			result.getModel().remove("finder");
			result.addObject("fixUpTasks", fixUpTasks);
			result.addObject("finder", finder);

		}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(final int fixUpTaskId, final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		FixUpTask fixUpTask = null;

		fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		Assert.notNull(fixUpTask);

		result = this.ShowModelAndView(fixUpTask);

		return result;

	}

	//ShowModelAndView--------------------------------------------------------------------

	protected ModelAndView ShowModelAndView(final FixUpTask fixUpTask) {
		ModelAndView result;
		result = this.ShowModelAndView(fixUpTask, null);
		return result;
	}

	protected ModelAndView ShowModelAndView(final FixUpTask fixUpTask, final String message) {
		final ModelAndView result;
		final Collection<Warranty> warranties = this.warrantyService.warrantiesFinalMode();
		final Collection<Category> categories = this.categoryService.findAll();

		final int varTax = this.configurationService.findOne().getVarTax();

		result = new ModelAndView("fixUpTask/edit");
		result.addObject("message1", message);
		result.addObject("requestURI", "fixUpTask/handyworker/show.do?fixUptaskId=" + fixUpTask.getId());
		result.addObject("fixUpTask", fixUpTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("varTax", varTax);
		result.addObject("isRead", true);

		return result;
	}

}

package controllers.Administrator;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.WarrantyService;
import controllers.AbstractController;
import domain.Warranty;

@Controller
@RequestMapping("/warranty/administrator")
public class WarrantyAdministratorController extends AbstractController {

	// Service---------------------------------------------------------

	@Autowired
	private WarrantyService warrantyService;

	// Constructor-----------------------------------------------------

	public WarrantyAdministratorController() {
		super();
	}

	// List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView modelAndView;
		final Collection<Warranty> warranties = this.warrantyService.findAll();

		modelAndView = new ModelAndView("warranty/list");
		modelAndView.addObject("warranties", warranties);
		modelAndView.addObject("requestURI", "/warranty/administrator/list.do");

		return modelAndView;

	}

	// Create-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Warranty warranty;

		warranty = this.warrantyService.create();
		result = this.createEditModelAndView(warranty);

		return result;
	}

	// Edit-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId,
			final RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Warranty warranty = this.warrantyService.findOne(warrantyId);

		try {
			Assert.notNull(warranty);
			Assert.isTrue(!warranty.getIsFinal());
			result = this.createEditModelAndView(warranty);

		} catch (Throwable e) {
			result = new ModelAndView(
					"redirect:/warranty/administrator/list.do");
			if (warranty == null)
				redirectAttrs.addFlashAttribute("message",
						"warranty.error.unexist");
			else if (warranty.getIsFinal() == true)
				redirectAttrs.addFlashAttribute("message",
						"application.error.isFinal");
		}
		return result;
	}

	// Save-------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Warranty warranty,
			final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(warranty);
		else
			try {
				this.warrantyService.save(warranty);
				result = new ModelAndView(
						"redirect:/warranty/administrator/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(warranty,
						"warranty.commit.error");
			}
		return result;
	}

	// Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Warranty warranty,
			final BindingResult binding) {
		ModelAndView result;

		try {
			this.warrantyService.delete(warranty);
			result = new ModelAndView(
					"redirect:/warranty/administrator/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warranty,
					"warranty.commit.error");
		}
		return result;
	}

	// ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Warranty warranty) {
		ModelAndView result;

		result = this.createEditModelAndView(warranty, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Warranty warranty,
			final String message) {
		ModelAndView result;

		result = new ModelAndView("warranty/edit");
		result.addObject("warranty", warranty);
		result.addObject("message", message);
		result.addObject("isRead", false);

		result.addObject("requestURI", "warranty/administrator/edit.do");

		return result;
	}
}

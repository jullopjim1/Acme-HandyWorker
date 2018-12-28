
package controllers.HandyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SectionService;
import controllers.AbstractController;
import domain.Section;

@Controller
@RequestMapping("/section/handyworker/")
public class SectionHandyWorkerController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private SectionService	sectionService;


	//Constructor---------------------------------------------------------

	public SectionHandyWorkerController() {
		super();
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int tutorialId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.create(tutorialId);
		result = this.createEditModelAndView(section);

		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int sectionId) {
		ModelAndView result;
		Section section;

		section = this.sectionService.findOne(sectionId);
		Assert.notNull(section);
		result = this.createEditModelAndView(section);

		return result;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Section section, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(section);
		else
			try {
				this.sectionService.save(section);
				result = new ModelAndView("redirect:/section/handyworker/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(section, "section.commit.error");
			}
		return result;
	}

	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Section section, final BindingResult binding) {

		ModelAndView result;

		try {
			this.sectionService.delete(section);
			result = new ModelAndView("redirect:/section/handyworker/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(section, "section.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section) {
		ModelAndView result;

		result = this.createEditModelAndView(section, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Section section, final String message) {
		ModelAndView result;

		result = new ModelAndView("section/edit");
		result.addObject("section", section);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "section/handyworker/edit.do");

		return result;
	}
}

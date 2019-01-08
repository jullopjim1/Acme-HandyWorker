
package controllers.HandyWorker;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.FixUpTaskService;
import controllers.AbstractController;
import domain.FixUpTask;

@Controller
@RequestMapping("/fixUpTask/handyWorker")
public class FixUpTaskHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	// Constructor---------------------------------------------------------

	public FixUpTaskHandyWorkerController() {
		super();
	}

	// List ---------------------------------------------------------------
		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<FixUpTask> fixUpTasks;

			fixUpTasks = this.fixUpTaskService.findAll();
			final String language = LocaleContextHolder.getLocale().getLanguage();

			result = new ModelAndView("fixUpTask/list");
			result.addObject("fixUpTasks", fixUpTasks);
			result.addObject("requestURI", "fixUpTask/handyWorker/list.do");
			result.addObject("lang", language.toUpperCase());

			return result;
		}
}


package controllers.HandyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.HandyWorkerService;
import controllers.AbstractController;
import domain.HandyWorker;

@Controller
@RequestMapping("/make/handyworker/")
public class MakeHandyWorkerController extends AbstractController {

	// Services-----------------------------------------------------------

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor---------------------------------------------------------

	public MakeHandyWorkerController() {
		super();
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(handyWorker);
		result = this.createEditModelAndView(handyWorker);

		return result;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final HandyWorker handyWorker, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(handyWorker);
		else
			try {
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(handyWorker, "actor.commit.error");

			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker) {
		ModelAndView result;

		result = this.createEditModelAndView(handyWorker, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker, final String message) {
		ModelAndView result;

		result = new ModelAndView("actor/make");
		result.addObject("handyworker", handyWorker);
		result.addObject("message1", message);
		result.addObject("isRead", false);

		result.addObject("requestURI", "make/handyworker/edit.do");

		return result;
	}
}

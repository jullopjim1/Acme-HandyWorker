
package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.EndorserService;
import controllers.AbstractController;
import domain.Endorser;

@Controller
@RequestMapping("/endorsement/administrator")
public class EndorsementAdministratorController extends AbstractController {

	@Autowired
	private EndorserService	endorserService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result = new ModelAndView();

		try {
			result = this.listCalculateModelAndView();
		} catch (final Exception e) {

			result.setViewName("welcome/index.do");
			result.addObject("message1", "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/calculateScore", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView modelAndView;
		try {
			// llamar a calcular score
			this.endorserService.updateScoreEndorsers();
			modelAndView = this.listCalculateModelAndView("endorsement.commit.ok");
		} catch (final Exception e) {
			modelAndView = this.listCalculateModelAndView("message.commit.error");
		}

		return modelAndView;
	}

	protected ModelAndView listCalculateModelAndView() {
		ModelAndView result;

		result = this.listCalculateModelAndView(null);

		return result;

	}

	protected ModelAndView listCalculateModelAndView(final String message) {
		ModelAndView result;

		final Collection<Endorser> endorser = this.endorserService.findAll();

		result = new ModelAndView("administrator/endorser/list");
		result.addObject("endorser", endorser);
		result.addObject("message1", message);
		return result;
	}

}

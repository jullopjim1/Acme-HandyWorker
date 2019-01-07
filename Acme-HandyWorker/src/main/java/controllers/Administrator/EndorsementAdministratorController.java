
package controllers.Administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.EndorsementService;
import services.EndorserService;
import controllers.AbstractController;
import domain.Endorser;

@Controller
@RequestMapping("/endorsement/administrator")
public class EndorsementAdministratorController extends AbstractController {

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private EndorserService		endorserService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;

		try {
			result = this.listCalculateModelAndView();
		} catch (final Exception e) {
			result.setViewName("welcome/index.do");
			result.addObject("message", "message.commit.error");
		}

		return result;
	}

	@RequestMapping(value = "/calculateScore", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView modelAndView;
		try {
			// llamar a calcular score
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

		result = new ModelAndView("endorsement/administrator/listEndorser");
		result.addObject("endorser", endorser);
		result.addObject("message", message);
		return result;
	}

}

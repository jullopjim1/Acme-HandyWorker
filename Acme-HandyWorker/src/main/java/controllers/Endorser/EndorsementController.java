
package controllers.Endorser;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.EndorsementService;
import services.EndorserService;
import controllers.AbstractController;
import domain.Endorsement;
import domain.Endorser;

@Controller
@RequestMapping("/endorsement")
public class EndorsementController extends AbstractController {

	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private EndorserService		endorserService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		Collection<Endorsement> endorsements = new ArrayList<>();
		result = new ModelAndView("endorsement/list");
		try {
			final Endorser endorser = this.endorserService.findEndorserByUseraccount(LoginService.getPrincipal());
			endorsements = this.endorsementService.findByEndorser(endorser);
			final Collection<Endorsement> endorsementsEndorsee = this.endorsementService.findByEndorsee(endorser);
			final Double score = endorser.getScore();
			result.addObject("score", score);
			result.addObject("endorsements", endorsements);
			result.addObject("endorsementsEndorsee", endorsementsEndorsee);
			result.addObject("requestURI", "endorsements/list.do");
		} catch (final Exception e) {
			result.setViewName("welcome/index.do");
			result.addObject("message", "message.commit.error");
		}

		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final Endorsement endorsement = this.endorsementService.create();

		modelAndView = this.createEditModelAndView(endorsement);

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId) {
		ModelAndView modelAndView;
		try {

			final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			this.endorsementService.checkPrincipal(endorsement);
			modelAndView = this.createEditModelAndView(endorsement);
		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/endorsement/list.do");
		}

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Endorsement endorsement, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement);
		else
			try {
				this.endorsementService.save(endorsement);
				result = new ModelAndView("redirect:/endorsement/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "message.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView save(final Endorsement endorsement) {

		ModelAndView result;
		try {
			this.endorsementService.delete(endorsement);
			result = new ModelAndView("redirect:/endorsement/list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorsement, "message.commit.error");
		}
		return result;
	}
	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String message) {
		ModelAndView result;

		final Collection<Endorser> endorsees = this.endorserService.findEndorsees();

		result = new ModelAndView("endorsement/edit");
		result.addObject("endorsement", endorsement);
		result.addObject("endorsees", endorsees);
		result.addObject("message", message);
		return result;
	}

}
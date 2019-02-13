
package controllers.Endorser;

import java.util.ArrayList;
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

import security.LoginService;
import services.CustomerService;
import services.EndorsementService;
import services.EndorserService;
import services.HandyWorkerService;
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

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private CustomerService		customerService;


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
			result.setViewName("redirect:/welcome/index.do");
			result.addObject("message1", "message.commit.error");
		}

		return result;
	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView modelAndView;
		try {
			final Endorsement endorsement = this.endorsementService.create();

			modelAndView = this.createEditModelAndView(endorsement);

		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/welcome/index.do");
			modelAndView.addObject("message1", "message.commit.error");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int endorsementId, final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView;
		final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
		try {
			Assert.notNull(endorsement);
			this.endorsementService.checkPrincipal(endorsement);
			modelAndView = this.createEditModelAndView(endorsement);
			modelAndView.addObject("isRead", true);
		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/endorsement/list.do");

			if (endorsement == null)
				redirectAttrs.addFlashAttribute("message1", "endorsement.error.unexist");
			else
				redirectAttrs.addFlashAttribute("message1", "tutorial.error.notActualCustomer");
		}

		return modelAndView;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorsementId, final RedirectAttributes redirectAttrs) {
		ModelAndView modelAndView;
		final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
		try {
			Assert.notNull(endorsement);
			this.endorsementService.checkPrincipal(endorsement);
			modelAndView = this.createEditModelAndView(endorsement);
		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/endorsement/list.do");

			if (endorsement == null)
				redirectAttrs.addFlashAttribute("message1", "endorsement.error.unexist");
			else
				redirectAttrs.addFlashAttribute("message1", "tutorial.error.notActualCustomer");
		}

		return modelAndView;
	}

	// Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Endorsement endorsement, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement, "message.commit.error");
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
	public ModelAndView delete(final Endorsement endorsement, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(endorsement, "message.commit.error");
		else
			try {
				this.endorsementService.delete(endorsement);
				result = new ModelAndView("redirect:/endorsement/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorsement, "message.commit.error");
			}
		return result;
	}

	// CreateModelAndView

	protected ModelAndView createEditModelAndView(final Endorsement endorsement) {
		ModelAndView result;

		result = this.createEditModelAndView(endorsement, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Endorsement endorsement, final String message) {
		ModelAndView result;

		Collection<Endorser> endorsees = new ArrayList<>();
		endorsees = this.endorserService.findEndorsees();

		if (endorsees.isEmpty()) {
			result = this.list();

			result.addObject("message1", "endorsement.listEndorsee.empty");

		} else {

			result = new ModelAndView("endorsement/edit");
			result.addObject("endorsement", endorsement);
			if (endorsees != null)
				result.addObject("endorsees", endorsees);
			result.addObject("message1", message);
			result.addObject("isRead", false);
		}
		return result;
	}
}

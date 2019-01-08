
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import services.ActorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SponsorService;
import domain.Actor;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	@Autowired
	private ActorService		actorService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private SponsorService		sponsorService;


	//Register handyWorker
	@RequestMapping(value = "/actor", method = RequestMethod.GET)
	public ModelAndView createHandyWorker(@RequestParam final String authority) {
		ModelAndView modelAndView;
		try {
			Actor actor = null;
			// Faltan actores
			switch (authority) {
			case "HANDY":
				actor = this.actorService.create(Authority.HANDY);
				break;
			case "CUSTOMER":
				actor = this.actorService.create(Authority.CUSTOMER);
				break;
			case "SPONSOR":
				actor = this.actorService.create(Authority.SPONSOR);
				break;
			default:
				throw new NullPointerException();
			}

			modelAndView = this.createEditModelAndView(actor);

		} catch (final Exception e) {
			modelAndView = new ModelAndView("welcome/index.do");
			modelAndView.addObject("message", "message.commit.error");
		}

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/actor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				this.actorService.update(actor);
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(actor, "message.commit.error");
			}
		return result;
	}

	//CreateModelAndView
	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result = null;
		final String authority = actor.getUserAccount().getAuthorities().iterator().next().getAuthority();
		//TODO faltan actores
		switch (authority) {
		case "HANDY":
			result = new ModelAndView("register/handyWorker");
			break;
		case "CUSTOMER":
			result = new ModelAndView("register/customer");
			break;
		case "SPONSOR":
			result = new ModelAndView("register/sponsor");
			break;
		default:
			throw new NullPointerException();
		}

		result.addObject("actor", actor);
		result.addObject("message", message);
		result.addObject("isRead", false);
		return result;
	}

}

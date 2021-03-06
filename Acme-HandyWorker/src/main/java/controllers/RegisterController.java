
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.UserAccountRepository;
import services.ActorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.SponsorService;
import domain.Actor;

@Controller
@RequestMapping("/register")
public class RegisterController extends AbstractController {

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Register handyWorker
	@RequestMapping(value = "/actor", method = RequestMethod.GET)
	public ModelAndView createHandyWorker(@RequestParam(required = false, defaultValue = "default") final String authority) {
		ModelAndView modelAndView;
		try {
			Actor actor = null;
			// Faltan actores
			switch (authority) {
			case "HANDY":
				actor = this.actorService.create("HANDY");
				break;
			case "CUSTOMER":
				actor = this.actorService.create("CUSTOMER");
				break;
			case "SPONSOR":
				actor = this.actorService.create("SPONSOR");
				break;
			default:
				throw new NullPointerException();
			}

			modelAndView = this.createEditModelAndView(actor);

		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/welcome/index.do");
		}

		return modelAndView;
	}

	// Save
	@RequestMapping(value = "/actor", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Actor actor, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(actor);
		else
			try {
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				actor.getUserAccount().setPassword(encoder.encodePassword(actor.getUserAccount().getPassword(), null));
				actor.getUserAccount().setEnabled(true);
				this.actorService.update(actor);

				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (final Throwable oops) {
				System.out.println("=======" + oops.getMessage() + "=======");
				final Actor test = this.actorService.findActorByUsername(actor.getUserAccount().getUsername());

				if (test != null)
					result = this.createEditModelAndView(actor, "actor.userExists");
				else
					result = this.createEditModelAndView(actor, "message.commit.error");
			}
		return result;
	}

	// CreateModelAndView
	protected ModelAndView createEditModelAndView(final Actor actor) {
		ModelAndView result;

		result = this.createEditModelAndView(actor, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Actor actor, final String message) {
		ModelAndView result = null;

		// TODO faltan actores
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		final Authority handy = new Authority();
		handy.setAuthority("HANDY");
		final Authority cust = new Authority();
		cust.setAuthority("CUSTOMER");
		final Authority refer = new Authority();
		refer.setAuthority("REFEREE");
		final Authority spon = new Authority();
		spon.setAuthority("SPONSOR");
		final Authority admin = new Authority();
		admin.setAuthority("ADMIN");

		if (authorities.contains(handy))
			result = new ModelAndView("register/handyWorker");
		else if (authorities.contains(cust))
			result = new ModelAndView("register/customer");
		else if (authorities.contains(spon))
			result = new ModelAndView("register/sponsor");
		else
			throw new NullPointerException();

		result.addObject("actor", actor);
		result.addObject("message1", message);
		result.addObject("isRead", false);
		return result;
	}

}

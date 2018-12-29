
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorshipService;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsorship/sponsor")
public class SponsorshipController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private SponsorshipService	sponsorshipService;


	//Constructor---------------------------------------------------------

	public SponsorshipController() {
		super();
	}
	//List ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipService.findAll();

		result = new ModelAndView("sponsorship/list");
		result.addObject("sponsorships", sponsorships);
		result.addObject("requestURI", "sponsorship/sponsor/list.do");

		return result;
	}

}


package controllers;

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
import services.ActorService;
import services.BoxService;
import domain.Actor;
import domain.Box;

@Controller
@RequestMapping("/box/actor")
public class BoxController {

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final Integer boxId) {
		final ModelAndView result;
		Collection<Box> boxes = new ArrayList<>();
		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());

		if (boxId == null) {
			final Collection<Box> allBoxes = this.boxService.findBoxesByActorId(actor.getId());
			final Collection<Box> systemBoxes = this.boxService.findSystemBoxesByActorId(actor.getId());
			allBoxes.removeAll(systemBoxes);
			boxes.addAll(systemBoxes);
			boxes.addAll(allBoxes);
		} else {
			final Box box1 = this.boxService.findOne(boxId);
			boxes = box1.getSubboxes();
		}

		result = new ModelAndView("box/actor/list");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/actor/list");
		return result;
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView modelAndView;

		final Box box = this.boxService.create();

		modelAndView = this.createEditModelAndView(box);

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Box box, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(box);
		else
			try {
				this.boxService.save(box);
				result = new ModelAndView("redirect:/box/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(box, "box.commit.error");
			}
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Box box) {
		ModelAndView result;

		result = this.createEditModelAndView(box, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Box box, final String message) {
		ModelAndView result;

		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final int actorId = actor.getId();

		final Collection<Box> boxes = this.boxService.findBoxesByActorId(actorId);
		boxes.removeAll(this.boxService.findSystemBoxesByActorId(actorId));

		if (box.getId() != 0)
			boxes.removeAll(box.getSubboxes());

		final Collection<String> priorities = new ArrayList<>();
		priorities.add("HIGH");
		priorities.add("NEUTRAL");
		priorities.add("LOW");

		result = new ModelAndView("message/actor/exchangeMessage");
		result.addObject("box", box);
		result.addObject("boxes", boxes);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("requestURI", "message/actor/exchangeMessage.do");

		return result;
	}

}

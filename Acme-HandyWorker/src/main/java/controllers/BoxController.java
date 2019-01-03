
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
public class BoxController extends AbstractController {

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView result;
		final Collection<Box> boxes = new ArrayList<>();
		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Collection<Box> allBoxes = this.boxService.findBoxesByActorId(actor.getId());
		final Collection<Box> systemBoxes = this.boxService.findSystemBoxesByActorId(actor.getId());
		allBoxes.removeAll(systemBoxes);
		boxes.addAll(systemBoxes);
		boxes.addAll(allBoxes);

		result = new ModelAndView("box/actor/list");
		result.addObject("boxes", boxes);
		result.addObject("requestURI", "box/actor/list.do");
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

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int boxId) {
		ModelAndView modelAndView;
		try {

			final Box box = this.boxService.findOne(boxId);
			this.boxService.checkPrincipal(box);
			modelAndView = this.createEditModelAndView(box);
		} catch (final Exception e) {
			modelAndView = new ModelAndView("redirect:/box/actor/list.do");
		}

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

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView save(final Box box) {

		ModelAndView result;
		try {
			this.boxService.delete(box);
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

		result = new ModelAndView("box/actor/edit");
		result.addObject("box", box);
		result.addObject("boxes", boxes);
		result.addObject("message", message);
		return result;
	}

}

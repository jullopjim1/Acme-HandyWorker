
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
import services.MessageService;
import domain.Actor;
import domain.Box;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController {

	@Autowired
	private MessageService	messageService;

	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/actor/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int boxId) {
		final ModelAndView result;

		final Actor actor = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final Box box1 = this.boxService.findOne(boxId);
		//TODO Añadir try and cast
		final Box box = this.boxService.findBoxByActorIdAndName(actor.getId(), box1.getName());

		//Obtener resultados fixuptasks de finder
		final Collection<Message> messages = this.messageService.findByBox(box);

		result = new ModelAndView("message/actor/list");
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/actor/list");
		return result;
	}

	//Create
	@RequestMapping(value = "/administrator/broadcastMessage", method = RequestMethod.GET)
	public ModelAndView broadcastMessage() {
		final ModelAndView modelAndView;

		final Message message = this.messageService.create();

		modelAndView = this.createEditModelAndView(message);

		modelAndView.setViewName("message/administrator/broadcastMessage");

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/administrator/broadcastMessage", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBroadcast(@Valid final Message message, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				this.messageService.broadcastMessage(message);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
				result.setViewName("message/administrator/broadcastMessage");

			}
		return result;
	}

	//Create
	@RequestMapping(value = "/actor/exchangeMessage", method = RequestMethod.GET)
	public ModelAndView exchangeMessage() {
		final ModelAndView modelAndView;

		final Message message = this.messageService.create();

		modelAndView = this.createEditModelAndView(message);

		return modelAndView;
	}

	//Show
	@RequestMapping(value = "/actor/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam final int messageId) {
		final ModelAndView modelAndView;

		final Message message = this.messageService.findOne(messageId);

		modelAndView = this.createEditModelAndView(message);
		modelAndView.addObject("isRead", true);
		modelAndView.addObject("requestURI", "/actor/show.do?messageId=" + messageId);

		return modelAndView;
	}

	//Save
	@RequestMapping(value = "/exchangeMessage", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Message message, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(message);
		else
			try {
				this.messageService.save(message);
				result = new ModelAndView("redirect:/message/actor/list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(message, "message.commit.error");
			}
		return result;
	}

	//CreateModelAndView

	protected ModelAndView createEditModelAndView(final Message message) {
		ModelAndView result;

		result = this.createEditModelAndView(message, null);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Message entityMessage, final String message) {
		ModelAndView result;
		final Collection<Actor> actors = this.actorService.findAll();
		final Collection<String> priorities = new ArrayList<>();

		priorities.add("HIGH");
		priorities.add("NEUTRAL");
		priorities.add("LOW");

		result = new ModelAndView("message/actor/exchangeMessage");
		result.addObject("entityMessage", entityMessage);
		result.addObject("message", message);
		result.addObject("receivers", actors);
		result.addObject("isRead", false);
		result.addObject("requestURI", "message/actor/exchangeMessage.do");

		return result;
	}

}


package controllers.Referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.NoteService;
import controllers.AbstractController;
import domain.Actor;
import domain.Note;

@Controller
@RequestMapping("/note/referee")
public class NoteRefereeController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ActorService	actorService;


	//Constructor---------------------------------------------------------

	public NoteRefereeController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final int refereeId = a.getId();

		final Collection<Note> note = this.noteService.findNoteByRefereeId(a.getId());

		result = new ModelAndView("note/list");
		result.addObject("note", note);
		result.addObject("refereeId", refereeId);
		result.addObject("requestURI", "note/referee/list.do");

		return result;

	}

}

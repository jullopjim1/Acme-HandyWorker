
package controllers.Referee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import controllers.AbstractController;
import domain.Note;

@Controller
@RequestMapping("/note/referee")
public class NoteRefereeController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private NoteService	noteService;


	//Constructor---------------------------------------------------------

	public NoteRefereeController() {
		super();
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.create(reportId);
		result = this.createEditModelAndView(note);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note) {
		ModelAndView result;

		result = this.createEditModelAndView(note, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Note note, final String message) {
		ModelAndView result;

		result = new ModelAndView("note/edit");
		result.addObject("note", note);
		result.addObject("message", message);
		result.addObject("isRead", false);
		result.addObject("reportId", note.getReport().getId());

		result.addObject("requestURI", "note/referee/edit.do");

		return result;
	}
}
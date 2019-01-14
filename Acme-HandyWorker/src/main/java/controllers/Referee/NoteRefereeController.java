
package controllers.Referee;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.NoteService;
import services.RefereeService;
import controllers.AbstractController;
import domain.Note;
import domain.Referee;

@Controller
@RequestMapping("/note/referee")
public class NoteRefereeController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private RefereeService	refereeService;


	//Constructor---------------------------------------------------------

	public NoteRefereeController() {
		super();
	}

	@RequestMapping(value = "/myList", method = RequestMethod.GET)
	public ModelAndView myList() {
		ModelAndView result;

		final Referee a = this.refereeService.findByUseraccount(LoginService.getPrincipal());
		final int refereeId = a.getId();

		final Collection<Note> note = this.noteService.findNoteByRefereeId(a.getId());

		result = new ModelAndView("note/list");
		result.addObject("note", note);
		result.addObject("refereeId", refereeId);
		result.addObject("requestURI", "note/referee/myList.do");

		return result;

	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int reportId) {
		ModelAndView result;

		final Referee a = this.refereeService.findByUseraccount(LoginService.getPrincipal());
		final int refereeId = a.getId();

		final Note note = this.noteService.findNoteReportById(reportId);

		result = new ModelAndView("note/list");
		result.addObject("note", note);
		result.addObject("refereeId", refereeId);
		result.addObject("requestURI", "note/referee/list.do");

		return result;

	}

}

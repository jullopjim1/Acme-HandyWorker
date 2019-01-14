
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.NoteService;
import services.ReportService;
import domain.Note;

@Controller
@RequestMapping("/note")
public class NoteController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private NoteService		noteService;

	@Autowired
	private ReportService	reportService;


	//Constructor---------------------------------------------------------

	public NoteController() {
		super();
	}

	//List ---------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int reportId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findNoteReportById(reportId);

		result = new ModelAndView("note/list");
		result.addObject("note", note);

		result.addObject("requestURI", "note/list.do");
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int noteId) {
		ModelAndView result;
		Note note;

		note = this.noteService.findOne(noteId);
		Assert.notNull(note);
		result = this.createEditModelAndView(note);

		return result;
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

	//Save
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Note note, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(note);
		else
			try {
				this.noteService.save(note);
				result = new ModelAndView("redirect:/note/list.do?reportId=" + note.getReport().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(note, "note.commit.error");

			}
		return result;
	}
	//Delete
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Note note, final BindingResult binding) {

		ModelAndView result;

		try {
			this.noteService.delete(note);
			result = new ModelAndView("redirect:/note/referee/list.do?reportId=" + note.getReport().getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(note, "note.commit.error");
		}
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
		result.addObject("requestURI", "note/edit.do");

		return result;
	}
}

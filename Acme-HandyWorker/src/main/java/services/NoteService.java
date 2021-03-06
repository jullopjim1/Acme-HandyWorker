
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import domain.Note;

@Service
@Transactional
public class NoteService {

	// Managed repository ------------------------------ (Relacion con su propio repositorio)
	@Autowired
	private NoteRepository	noteRepository;

	@Autowired
	private ReportService	reportService;


	//Constructor----------------------------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods ------------------------------ (Operaciones b�sicas, pueden tener restricciones seg�n los requisitos)
	public Note create(final int ReportId) {
		Note note;

		note = new Note();
		note.setIsFinal(false);
		note.setReport(this.reportService.findOne(ReportId));
		note.setMoment(new Date(System.currentTimeMillis() - 1000));
		return note;
	}

	public Collection<Note> findAll() {
		Collection<Note> notes;

		notes = this.noteRepository.findAll();
		Assert.notNull(notes);

		return notes;
	}

	public Note findOne(final int NoteId) {
		Note note;
		note = this.noteRepository.findOne(NoteId);
		Assert.notNull(note);

		return note;
	}

	public Note save(final Note note) {
		Assert.notNull(note);
		Note result;

		note.setMoment(new Date(System.currentTimeMillis() - 1000));

		result = this.noteRepository.save(note);

		return result;
	}

	public Note findNoteReportById(final int reportId) {
		return this.noteRepository.findNoteReportById(reportId);
	}

	public Collection<Note> findNoteByRefereeId(final int refereeId) {
		return this.noteRepository.findNoteByRefereeId(refereeId);
	}

	public void delete(final Note note) {
		this.noteRepository.delete(note);
	}
}

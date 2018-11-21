
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


	//Constructor----------------------------------------------------------------------------

	public NoteService() {
		super();
	}

	// Simple CRUD methods ------------------------------ (Operaciones básicas, pueden tener restricciones según los requisitos)
	public Note create() {
		Note note;

		note = new Note();
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

}

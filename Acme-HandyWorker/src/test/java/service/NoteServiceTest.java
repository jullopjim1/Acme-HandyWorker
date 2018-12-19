
package service;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.NoteService;
import utilities.AbstractTest;
import domain.Note;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class NoteServiceTest extends AbstractTest {

	// Service ------------------------------
	@Autowired
	private NoteService	noteService;


	// Test
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		final int reportId = this.getEntityId("report1");
		try {
			final Note note = this.noteService.create(reportId);
			note.setMoment(new Date(System.currentTimeMillis() - 1000));
			note.setMandatoryCommentCustomer("aaa");
			note.setMandatoryCommentHandyWorker("eee");
			note.setMandatoryCommentReferee("pppp");
			Assert.notNull(note.getReport());

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int reportId = this.getEntityId("report1");

		try {
			final Note note = this.noteService.findNoteReportById(reportId);
			Assert.notNull(note);

			Assert.isTrue(note.getReport().getId() == reportId);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		Note note, saved;

		final int reportId = this.getEntityId("report1");
		try {
			note = this.noteService.create(reportId);
			note.setMandatoryCommentCustomer("aaaa");
			note.setMandatoryCommentHandyWorker("pppp");
			note.setMandatoryCommentReferee("eeee");

			saved = this.noteService.save(note);

			final Collection<Note> notes1 = this.noteService.findAll();
			Assert.isTrue(notes1.contains(saved));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final int noteId = this.getEntityId("note1");
		try {
			final Note note = this.noteService.findOne(noteId);
			final Collection<Note> notes = this.noteService.findAll();
			Assert.isTrue(notes.contains(note));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
}

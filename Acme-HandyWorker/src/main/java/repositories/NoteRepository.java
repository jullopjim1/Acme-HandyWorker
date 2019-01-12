
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select n from Note n where n.report.id = ?1")
	Note findNoteReportById(int reportId);

	@Query("select n from Note n where n.report.referee.id=?1")
	Collection<Note> findNoteByRefereeId(int refereeId);

	@Query("select n from Note n where n.report.complaint.customer.id=?1")
	Collection<Note> findNoteByCustomerId(int customerId);

	@Query("select n from Note n where n.report.complaint.fixUpTask.id=(select a.fixUpTask.id from Application a where a.handyWorker.id=?1 and a.status='ACCEPTED')")
	Collection<Note> findNoteByHandyWorkerId(int handyWorkerId);
}

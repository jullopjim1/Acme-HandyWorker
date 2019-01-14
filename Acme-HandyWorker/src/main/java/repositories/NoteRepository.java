
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

}

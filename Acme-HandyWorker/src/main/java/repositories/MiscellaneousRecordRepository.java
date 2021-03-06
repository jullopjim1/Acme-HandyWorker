
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.MiscellaneousRecord;

@Repository
public interface MiscellaneousRecordRepository extends JpaRepository<MiscellaneousRecord, Integer> {

	@Query("select p from MiscellaneousRecord p where p.curriculum.id=?1")
	public Collection<MiscellaneousRecord> findMiscellaneousRecordByCurriculumId(int curriculumId);
}


package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EducationRecordRepository extends JpaRepository<EducationRecord, Integer> {

	@Query("select p from EducationRecord p where p.curriculum.id=?1")
	public Collection<EducationRecord> findEducationRecordByCurriculumId(int curriculumId);
}

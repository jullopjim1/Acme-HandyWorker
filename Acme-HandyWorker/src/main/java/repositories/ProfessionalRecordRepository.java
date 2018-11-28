
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends JpaRepository<ProfessionalRecord, Integer> {

	@Query("select p from ProfessionalRecord p where p.curriculum.id=?1")
	public Collection<ProfessionalRecord> findProfessionalRecordByCurriculumId(int curriculumId);
}

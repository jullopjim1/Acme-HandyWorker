
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EndorserRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EndorserRecord, Integer> {

	@Query("select p from EndorserRecord p where p.curriculum.id=?1")
	public EndorserRecord findEndorserRecordByCurriculumId(int curriculumId);
}

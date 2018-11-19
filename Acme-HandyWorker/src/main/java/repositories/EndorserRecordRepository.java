
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EducationRecord;

@Repository
public interface EndorserRecordRepository extends JpaRepository<EducationRecord, Integer> {

}

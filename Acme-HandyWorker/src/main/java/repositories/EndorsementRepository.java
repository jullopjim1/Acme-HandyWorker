
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select sum(e.score) from Endorsement e where e.endorsee.id=?1")
	double calculateScoreByEndorser(int endorserId);

}

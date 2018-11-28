
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer> {

	@Query("select e from Endorser e where e.userAccount=?1")
	Endorser findEndorserByUseraccount(UserAccount userAccount);

}

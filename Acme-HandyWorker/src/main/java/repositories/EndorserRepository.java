
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Endorser;

@Repository
public interface EndorserRepository extends JpaRepository<Endorser, Integer> {

	@Query("select e from Endorser e where e.userAccount=?1")
	Endorser findEndorserByUseraccount(UserAccount userAccount);

	@Query("select a.handyWorker from Application a where a.status='ACCEPTED' and a.fixUpTask.customer.id = ?1")
	Collection<Endorser> findEndorseesByCustomer(int customerId);

	@Query("select a.fixUpTask.customer from Application a where a.status='ACCEPTED' and a.handyWorker.id= ?1")
	Collection<Endorser> findEndorseesByHandyWorker(int handyWorkerId);

}

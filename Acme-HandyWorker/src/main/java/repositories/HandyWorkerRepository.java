
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select a.handyWorker from Application a where a.status='ACCEPTED' and a.fixUpTask.id=(select p.fixUpTask.id from Phase p where p.id=?1)")
	public HandyWorker findHandyWorkerByPhaseId(int phaseId);

	@Query("select h from HandyWorker h where h.userAccount.id = ?1")
	public HandyWorker findHandyWorkerByUserAccount(int userAccountId);

	@Query("select h from HandyWorker h where h.userAccount.username=?1")
	HandyWorker findHandyByUsername(String username);
}

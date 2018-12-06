
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select a.handyWorker from Application a, Phase p where a.status like 'ACCEPTED' and p.id = ?1")
	public HandyWorker findHandyWorkerByPhaseId(int phaseId);

	@Query("select h from HandyWorker h where h.userAccount.id = ?1")
	public HandyWorker findHandyWorkerByUserAccount(int userAccountId);
}

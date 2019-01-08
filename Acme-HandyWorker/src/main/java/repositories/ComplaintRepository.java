
package repositories;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;
import domain.Customer;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	//Queries Level B-----------------------------------------------------------
	@Query("select c.customer from Complaint c group by c.customer.id order by count(c) desc")
	public ArrayList<Customer> queryB4();

	//Other Queries-------------------------------------------------------------
	@Query("select c from Complaint c where c.customer.id=?1")
	Collection<Complaint> findComplaintsByCustomerId(int customerId);

	@Query("select r.complaint from Report r where r.referee.id=?1")
	Collection<Complaint> findComplaintsByRefereeId(int refereeId);

	@Query("select c from Complaint c where c.fixUpTask.id=?1 and c.isFinal = true")
	Complaint findComplaintByTaskId(int fixUpTaskId);

	@Query("select c from Complaint c where c.fixUpTask.id=(select a.fixUpTask.id from Application a where a.handyWorker.id=?1 and a.status='ACCEPTED')")
	Collection<Complaint> findComplaintsByHandyWorkerId(int handyWorkerId);
}

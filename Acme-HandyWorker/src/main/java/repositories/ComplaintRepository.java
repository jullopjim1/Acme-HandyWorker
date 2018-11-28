
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	//Queries Level B-----------------------------------------------------------
	@Query("select c.customer.name from Complaint c group by c.customer.id order by count(c) desc")
	public Object[] queryB4();

	//Other Queries-------------------------------------------------------------
	@Query("select c from Complaint c where c.customer=?1")
	Collection<Complaint> findComplaintsByCustomerId(int customerId);

}

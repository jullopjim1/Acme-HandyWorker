
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	//Queries Level C---------------------------------------------------------
	@Query("select h.name from HandyWorker h where ((select count(a) from Application a where a.status = 'ACCEPTED') > (select avg(hh.applications.size)*1.1 from HandyWorker hh join hh.applications aa where aa.status = 'ACCEPTED')) order by h.applications.size desc")
	public Object[] queryC10();
}

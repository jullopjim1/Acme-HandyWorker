
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//Queries Level C---------------------------------------------------------------
	@Query("select avg(f.applications.size), min(f.applications.size), max(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	public Object[] queryC2();

	@Query("select avg(a.price), min(a.price), max(a.price), stddev(a.price) from Application a")
	public Object[] queryC4();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='PENDING'")
	public Double queryC5();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='ACCEPTED'")
	public Double queryC6();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='REJECTED'")
	public Double queryC7();

	@Query("select (count(a)*1.0)/(select count(aa) from Application aa where aa.fixUpTask > CURRENT_TIMESTAMP) from Application a")
	public Double queryC8();

	@Query("select h.name from HandyWorker h where ((select count(a) from Application a where a.status = 'ACCEPTED') > (select avg(hh.applications.size)*1.1 from HandyWorker hh join hh.applications aa where aa.status = 'ACCEPTED')) order by h.applications.size desc")
	public Object[] queryC10();

	//Queries Level B---------------------------------------------------------------

	//Other Queries-----------------------------------------------------------------
	@Query("select a from Application a where a.creditCard=?1")
	Collection<Application> findApplicationsByCreditCardId(int creditCardId);

}

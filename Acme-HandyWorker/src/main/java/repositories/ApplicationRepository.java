
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	// Queries Level
	// C---------------------------------------------------------------

	// Query
	// C2-------------------------------------------------------------------------
	@Query("select avg(1.0 * (select count(a) from Application a where a.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryC2AVG();

	@Query("select max(1.0 * (select count(a) from Application a where a.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryC2MAX();

	@Query("select min(1.0 * (select count(a) from Application a where a.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryC2MIN();

	@Query("select stddev(1.0 * (select count(a) from Application a where a.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryC2STDDEV();

	// QueryC4---------------------------------------------------------------------------
	@Query("select avg(a.price), min(a.price), max(a.price), stddev(a.price) from Application a")
	public Object[] queryC4();

	// QueryC5---------------------------------------------------------------------------
	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='PENDING'")
	public Double queryC5();

	// QueryC6---------------------------------------------------------------------------
	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='ACCEPTED'")
	public Double queryC6();

	// QueryC7---------------------------------------------------------------------------
	@Query("select (count(a)*1.0)/(select count(aa) from Application aa) from Application a where a.status='REJECTED'")
	public Double queryC7();

	// QueryC8---------------------------------------------------------------------------
	@Query("select (count(a)*1.0)/(select count(aa) from Application aa where aa.fixUpTask > CURRENT_TIMESTAMP) from Application a")
	public Double queryC8();

	// QueryC9---------------------------------------------------------------------------
	@Query("select c.name from Customer c where ((select count(f) from FixUpTask f)*1.0 > (select avg(1.0 * (select count(ff) from FixUpTask ff where ff.customer.id = c.id))*1.1 from Customer c)) order by (select count(aaa) from Application aaa)*1.0 desc")
	public Object[] queryC9();

	// QueryC10---------------------------------------------------------------------------
	@Query("select h.name from HandyWorker h where ((select count(a) from Application a where a.status = 'ACCEPTED')*1.0 > (select avg(1.0 * (select count(aa) from Application aa where aa.fixUpTask.id = f.id))*1.1 from FixUpTask f)) order by (select count(aaa) from Application aaa)*1.0 desc")
	public Object[] queryC10();

	// Queries Level
	// B---------------------------------------------------------------

	// Other
	// Queries-----------------------------------------------------------------
	@Query("select a from Application a where a.creditCard=?1")
	public Collection<Application> findApplicationsByCreditCardId(int creditCardId);

	@Query("select a from Application a where a.fixUpTask.id=?1")
	public Collection<Application> findApplicationsByFixUpTeaskId(int fixUpTaskId);

}

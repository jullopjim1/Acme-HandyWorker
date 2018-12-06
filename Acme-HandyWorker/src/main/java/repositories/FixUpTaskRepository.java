
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	// select avg(1.0 * (select count(b) from B b where b.a.id = a.id)) from A
	// a;
	// Queries LevelC-----------------------------------------------------------

	// Query
	// C1--------------------------------------------------------------------------------------------
	@Query("select avg(1.0 * (select count(f) from FixUpTask f where f.customer.id = c.id)) from Customer c")
	Double queryC1AVG();

	@Query("select max(1.0 * (select count(f) from FixUpTask f where f.customer.id = c.id)) from Customer c")
	Double queryC1MAX();

	@Query("select min(1.0 * (select count(f) from FixUpTask f where f.customer.id = c.id)) from Customer c")
	Double queryC1MIN();

	@Query("select stddev(1.0 * (select count(f) from FixUpTask f where f.customer.id = c.id)) from Customer c")
	Double queryC1STDDEV();

	// Query
	// C3---------------------------------------------------------------------------------------------

	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice), stddev(f.maxPrice) from FixUpTask f")
	public Object[] queryC3();

	// Queries Level B----------------------------------------------------------
	// Query C1
	@Query("select avg(1.0 * (select count(c) from Complaint c where c.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryB1AVG();

	@Query("select max(1.0 * (select count(c) from Complaint c where c.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryB1MAX();

	@Query("select min(1.0 * (select count(c) from Complaint c where c.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryB1MIN();

	@Query("select stddev(1.0 * (select count(c) from Complaint c where c.fixUpTask.id = f.id)) from FixUpTask f")
	Double queryB1STDDEV();

	// Query
	// B3---------------------------------------------------------------------
	@Query("select 1.0*(select count(c) from Complaint c where c.fixUpTask.id= f.id)/count(f) from FixUpTask f")
	public Double queryB3();

	@Query("select a.handyWorker.name from Application a where a.status = 'ACCEPTED' order by (select count(c) from Complaint c)*1.0 desc")
	public Object[] queryB5();

	// Other Queries------------------------------------------------------------
	@Query("select f from FixUpTask f where f.category.id = ?1")
	public Collection<FixUpTask> findTasksByCategoryId(int categoryId);

	@Query("select a.fixUpTask from Application a where a.status LIKE 'ACCEPTED' and a.handyWorker.id = ?1 and a.fixUpTask.deadline > CURRENT_TIMESTAMP")
	public Collection<FixUpTask> findTasksActiveByApplicationAcceptedAndHandyWorkerId(int handyWorkerId);

	@Query("select t from FixUpTask t where t.customer.id = ?1")
	public Collection<FixUpTask> findFixUpTaskByCustomerId(int customerId);

}

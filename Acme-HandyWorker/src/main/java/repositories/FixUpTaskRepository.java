
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	//Queries Level C-----------------------------------------------------------
	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	public Object[] queryC1();

	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice), stddev(f.maxPrice) from FixUpTask f")
	public Object[] queryC3();

	//Queries Level B----------------------------------------------------------
	@Query("select avg(f.complaints.size), min(f.complaints.size), max(f.complaints.size), stddev(f.complaints.size) from FixUpTask f")
	public Object[] queryB1();

	@Query("select 1.0*(select count(b) from FixUpTask b where b.complaints.size=1)/count(a) from FixUpTask a")
	public Double queryB3();

	@Query("select a.handyWorker from FixUpTask f join f.applications a where a.status = 'ACCEPTED' order by f.complaints.size desc")
	public Object[] queryB5();

	//Other Queries------------------------------------------------------------
	@Query("select f from FixUpTask f where f.category.id = ?1")
	public Collection<FixUpTask> findTasksByCategoryId(int categoryId);

	@Query("select t from FixUpTask t join t.applications a where a.status LIKE 'ACCEPTED' and a.handyWorker.id = ?1 and t.deadline > CURRENT_TIMESTAMP")
	public Collection<FixUpTask> findTasksActiveByApplicationAcceptedAndHandyWorkerId(int handyWorkerId);

}

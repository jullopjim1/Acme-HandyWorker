
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//Queries C

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size), max(c.fixUpTasks.size), stddev(c.fixUpTasks.size) from Customer c")
	public Object[] queryC1();

	@Query("select avg(f.applications.size), min(f.applications.size), max(f.applications.size), stddev(f.applications.size) from FixUpTask f")
	public Object[] queryC2();

	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice), stddev(f.maxPrice) from FixUpTask f")
	public Object[] queryC3();

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

	@Query("select c from Customer c where c.fixUpTasks.size > (select avg(c.fixUpTasks.size)*1.10 from Customer c)")
	public Double queryC9();

	@Query("select h.name from HandyWorker h where ((select count(a) from Application a where a.status = 'ACCEPTED') > (select avg(hh.applications.size)*1.1 from HandyWorker hh join hh.applications aa where aa.status = 'ACCEPTED')) order by h.applications.size desc")
	public Object[] queryC10();

	//Queries B
	@Query("select avg(f.complaints.size), min(f.complaints.size), max(f.complaints.size), stddev(f.complaints.size) from FixUpTask f")
	public Object[] queryB1();

	@Query("select avg(r.note.size), min(r.note.size), max(r.note.size), stddev(r.note.size) from Report r")
	public Object[] queryB2();

	@Query("select 1.0*(select count(b) from FixUpTask b where b.complaints.size=1)/count(a) from FixUpTask a")
	public Double queryB3();

	@Query("select c.customer.name from Complaint c group by c.customer.id order by count(c) desc")
	public Object[] queryB4();

	@Query("select a.handyWorker from FixUpTask f join f.applications a where a.status = 'ACCEPTED' order by f.complaints.size desc")
	public Object[] queryB5();

}

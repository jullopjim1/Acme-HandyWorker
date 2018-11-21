
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//Queries C

	@Query("select avg(c.fixUpTasks.size), min(c.fixUpTasks.size)*, max(c.fixUpTasks.size)*, stddev(c.fixUpTasks.size) from Customer c")
	Object[] queryC1();

	@Query("select avg(f.applications.size), min(f.applications.size)*, max(f.applications.size)*, stddev(f.applications.size) from FixUpTask f")
	Object[] queryC2();

	@Query("select avg(f.maxPrice), min(f.maxPrice), max(f.maxPrice), stddev(f.maxPrice) from FixUpTask f")
	Object[] queryC3();

	@Query("select avg(a.price), min(a.price), max(a.price), stddev(a.price) from Application a")
	Object[] queryC4();

	@Query("select 1.0*(select count(b) from Application b where b.status=PENDING)/count(a) from Application a")
	Double queryC5();

	@Query("select 1.0*(select count(b) from Application b where b.status='ACCEPTED')/count(a) from Appplication a")
	Double queryC6();

	@Query("select 1.0*(select count(b) from Application b where b.status='REJECTED')/count(a) from Application a")
	Double queryC7();

	@Query("select 1.0*(select count(b) from Application b where b.fixuptask > CURRENT_TIMESTAMP)/count(a) from Application a")
	Double queryC8();

	@Query("select c from Customer c where c.fixUpTasks.size > (select avg(c.fixUpTasks.size)*1.10 from Customer c)")
	Double queryC9();

	@Query("select h.name from HandyWorker h where ((select count(a) from Application a where a.status = 'ACCEPTED') > (select avg(hh.applications.size)*1.1 from HandyWorker hh join hh.applications aa where aa.status = 'ACCEPTED')) order by h.applications.size desc")
	Object[] queryC10();

	//Queries B
	@Query("select avg(f.complaints.size), min(f.complaints.size)*, max(f.complaints.size)*, stddev(f.complaints.size) from FixUpTask f")
	Object[] queryB1();

	@Query("select avg(r.note.size), min(r.note.size), max(r.note.size), stddev(r.note.size) from Report r")
	Object[] queryB2();

	@Query("select 1.0*(select count(b) from FixUpTask b where b.complaints.size=1)/count(a) from FixUpTask a")
	Double queryB3();

	@Query("select c.customer.name from Complaint c group by c.customer.id order by count(c) desc")
	Object[] queryB4();

	@Query("select a.handyWorker from FixUpTask f join f.applications a where a.status = 'ACCEPTED' order by f.complaints.size desc")
	Object[] queryB5();

}

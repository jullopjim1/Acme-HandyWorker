
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Application;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	//Queries C

	@Query("select avg(c.fixUpTasks.size)from Customer c")
	Double aritmeticaCustomerMed();
	@Query("select min(c.fixUpTasks.size)from Customer c")
	Double aritmeticaCustomerMin();
	@Query("select max(c.fixUpTasks.size)from Customer c")
	Double aritmeticaCustomerMax();
	@Query("select stddev(c.fixUpTasks.size)from Customer c")
	Double aritmeticaCustomerDes();

	@Query("select avg(f.applications.size)from FixUpTask f")
	Double aritmeticaFixUpTaskMed();
	@Query("select min(f.applications.size)from FixUpTask f")
	Double aritmeticaFixUpTaskMin();
	@Query("select max(f.applications.size)from FixUpTask f")
	Double aritmeticaFixUpTaskMax();
	@Query("select stddev(f.applications.size)from FixUpTask f")
	Double aritmeticaFixUpTaskDes();

	@Query("select avg(f.maxPrice)from FixUpTask f")
	Double aritmeticaFixUpTaskPriceMed();
	@Query("select min(f.maxPrice)from FixUpTask f")
	Double aritmeticaFixUpTaskPriceMin();
	@Query("select max(f.maxPrice)from FixUpTask f")
	Double aritmeticaFixUpTaskPriceMax();
	@Query("select stddev(f.maxPrice)from FixUpTask f")
	Double aritmeticaFixUpTaskPriceDes();

	@Query("select avg(a.price)from Application a")
	Double aritmeticaAppMed();
	@Query("select min(a.price)from Application a")
	Double aritmeticaAppMin();
	@Query("select max(a.price)from Application a")
	Double aritmeticaAppMax();
	@Query("select stddev(a.price)from Application a")
	Double aritmeticaAppDes();

	@Query("select 1.0*(select count(b) from Application b where b.status='PENDING')/count(a) from Application a")
	Double appsStatusPending();

	@Query("select 1.0*(select count(b) from Application b where b.status='ACCEPTED')/count(a) from Appplication a")
	Double appsStatusAccepted();

	@Query("select 1.0*(select count(b) from Application b where b.status='REJECTED')/count(a) from Application a")
	Double appsStatusRejected();

	@Query("select 1.0*(select count(b) from Application b where b.fixuptask > CURRENT_TIMESTAMP)/count(a) from Application a")
	Collection<Application> findByApplicationId(int applicationId);

	//Queries B
	@Query("select avg(f.complaints.size)from FixUpTask f")
	Double aritmeticaFixUpTaskComplaintMed();
	@Query("select min(f.complaints.size)from FixUpTask f")
	Double aritmeticaFixUpTaskComplaintMin();
	@Query("select max(f.complaints.size)from FixUpTask f")
	Double aritmeticaFixUpTaskComplaintMax();
	@Query("select stddev(f.complaints.size)from FixUpTask f")
	Double aritmeticaFixUpTaskComplaintDes();

	@Query("select avg(r.note.size)from Report r")
	Double aritmeticaReportMed();
	@Query("select min(r.note.size)from Report r")
	Double aritmeticaReportMin();
	@Query("select max(r.note.size)from Report r")
	Double aritmeticaReportMax();
	@Query("select stddev(r.note.size)from Report r")
	Double aritmeticaReportDes();

	@Query("select 1.0*(select count(b) from FixUpTask b where b.complaints.size=1)/count(a) from FixUpTask a")
	Double findWithOneComplaint();

}

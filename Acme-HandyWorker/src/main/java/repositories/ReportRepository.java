
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	// Queries Level B-----------------------------------------------------

	@Query("select avg(1.0 * (select count(f) from Note f where f.report.id = c.id)) from Report c")
	Double queryB2AVG();

	@Query("select max(1.0 * (select count(f) from Note f where f.report.id = c.id)) from Report c")
	Double queryB2MAX();

	@Query("select min(1.0 * (select count(f) from Note f where f.report.id = c.id)) from Report c")
	Double queryB2MIN();

	@Query("select stddev(1.0 * (select count(f) from Note f where f.report.id = c.id)) from Report c")
	Double queryB2STDDEV();

}

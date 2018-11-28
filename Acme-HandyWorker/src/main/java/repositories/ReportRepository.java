
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	//Queries Level B-----------------------------------------------------
	@Query("select avg(r.note.size), min(r.note.size), max(r.note.size), stddev(r.note.size) from Report r")
	public Object[] queryB2();

}

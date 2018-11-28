
package repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from FixUpTask f where (f.ticker like '%?1%' or f.description like '%?1%' or f.address like '%?1%') and (f.deadline BETWEEN ?2 and ?3) and (f.maxPrice BETWEEN ?4 and ?5) and (f.category.name like '%?6%') and (f.warranty.title like '%?7%')")
	Page<FixUpTask> searchFixUpTasks(String keyword, Date dateMin, Date dateMax, Double priceMin, Double priceMax, String namecategory, String namewarranty, Pageable pageable);

	@Query("select h.finder from HandyWorker h where h.id=?1")
	Finder findByHandyWorker(int handyWorkerId);

	//	@Query("select f from FixUpTask f where f.maxPrice between 0.0 and ?1")
	//	Page<FixUpTask> searchFixUpTasks(double maxPrice, Pageable pageable);

}

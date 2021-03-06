
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.FixUpTask;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	@Query("select f from FixUpTask f where (f.ticker.ticker like %:keyword% or f.description like %:keyword% or f.adress like %:keyword%) and (f.deadline BETWEEN :dateMin and :dateMax) and (f.maxPrice BETWEEN :priceMin and :priceMax) and (f.category = (select c from Category c join c.name k where (key(k) like %:langCategory%) and (k like %:nameCategory%))) and (f.warranty.title like %:namewarranty%)")
	Page<FixUpTask> searchFixUpTasks(@Param("keyword") String keyword, @Param("dateMin") Date dateMin, @Param("dateMax") Date dateMax, @Param("priceMin") Double priceMin, @Param("priceMax") Double priceMax, @Param("langCategory") String langCategory,
		@Param("nameCategory") String namecategory, @Param("namewarranty") String namewarranty, Pageable pageable);

	@Query("select f from FixUpTask f where (f.ticker.ticker like %:keyword% or f.description like %:keyword% or f.adress like %:keyword%) and (f.deadline BETWEEN :dateMin and :dateMax) and (f.maxPrice BETWEEN :priceMin and :priceMax) and (f.warranty.title like %:namewarranty%)")
	Page<FixUpTask> searchFixUpTasks(@Param("keyword") String keyword, @Param("dateMin") Date dateMin, @Param("dateMax") Date dateMax, @Param("priceMin") Double priceMin, @Param("priceMax") Double priceMax, @Param("namewarranty") String namewarranty,
		Pageable pageable);

	@Query("select h.finder from HandyWorker h where h.id=?1")
	Finder findByHandyWorker(int handyWorkerId);
	
	@Query("select f from Finder f where ?1 member of f.fixUpTasks")
	public Collection<Finder> findersByFixUpTask(int fixUpTaskId);
}

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {
	
	@Query("select f from FixUpTask f where f.category.id = ?1")
	Collection<FixUpTask> findTasksByCategoryId(int categoryId);

}
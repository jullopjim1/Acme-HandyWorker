
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Referee;

@Repository
public interface RefereeRepository extends JpaRepository<Referee, Integer> {

	@Query("select h from Referee h where h.userAccount.id = ?1")
	public Referee findRefereeByUserAccount(int userAccountId);

	@Query("select r from Referee r where r.userAccount.username=?1")
	Referee findRefereeByUsername(String username);
}

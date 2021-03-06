
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Integer> {

	@Query("select h from Sponsor h where h.userAccount.id = ?1")
	public Sponsor findSponsorByUserAccount(int userAccountId);

	@Query("select s from Sponsor s where s.userAccount.username=?1")
	Sponsor findSponsorByUsername(String username);
}

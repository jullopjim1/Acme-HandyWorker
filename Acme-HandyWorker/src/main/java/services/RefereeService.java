
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Referee;
import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;

@Service
@Transactional
public class RefereeService {

	public Referee findRefereeByUsername(final String username) {
		return this.refereeRepository.findRefereeByUsername(username);
	}


	// Repository-----------------------------------------------
	@Autowired
	private RefereeRepository refereeRepository;


	// Services-------------------------------------------------
	// Constructor----------------------------------------------
	public RefereeService() {
		super();
	}
	// Simple CRUD----------------------------------------------

	public Referee create() {
		final Referee referee = new Referee();

		final Collection<Authority> authorities = new ArrayList<Authority>();
		final UserAccount userAccount = new UserAccount();

		final Authority a = new Authority();
		a.setAuthority("REFEREE");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		referee.setUserAccount(userAccount);

		referee.setIsBanned(false);
		referee.setIsSuspicious(false);

		return referee;
	}

	public List<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(final Integer refereeId) {
		return this.refereeRepository.findOne(refereeId);
	}

	public Referee save(final Referee referee) {
		Assert.notNull(referee);
		final Referee saved = this.refereeRepository.save(referee);
		return saved;
	}

	public void delete(final Referee entity) {
		this.refereeRepository.delete(entity);

	}
	// Other Methods--------------------------------------------

	public Referee isSuspicious(final Referee referee) {
		final Referee saved = this.refereeRepository.save(referee);

		return saved;
	}

	public Referee findByUseraccount(final UserAccount userAccount) {

		return this.refereeRepository.findRefereeByUserAccount(userAccount.getId());

	}

}

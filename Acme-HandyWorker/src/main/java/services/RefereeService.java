
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class RefereeService {

	//Repository-----------------------------------------------
	@Autowired
	private RefereeRepository	refereeRepository;


	//Services-------------------------------------------------
	//Constructor----------------------------------------------
	public RefereeService() {
		super();
	}
	//Simple CRUD----------------------------------------------

	public Referee create() {
		final Referee referee = new Referee();

		final Collection<Report> reports = new ArrayList<>();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final UserAccount userAccount = new UserAccount();

		referee.setReports(reports);

		final Authority a = new Authority();
		a.setAuthority("REFEREE");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
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
	//Other Methods--------------------------------------------

}

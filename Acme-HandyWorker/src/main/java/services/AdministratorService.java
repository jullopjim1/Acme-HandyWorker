
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;

@Service
@Transactional
public class AdministratorService {

	//Repository-----------------------------------------------

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Services-------------------------------------------------
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private CustomerService			customerService;


	//Constructor----------------------------------------------

	public AdministratorService() {

		super();
	}

	//Simple CRUD----------------------------------------------

	public Administrator create() {
		final Administrator administrator = new Administrator();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority("ADMIN");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		administrator.setUserAccount(userAccount);

		administrator.setIsBanned(false);
		administrator.setIsSuspicious(false);
		return administrator;
	}
	public List<Administrator> findAll() {
		return this.administratorRepository.findAll();
	}

	public Administrator findOne(final Integer administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);
		final Administrator saved = this.administratorRepository.save(administrator);
		return saved;
	}
	public void delete(final Administrator entity) {
		this.administratorRepository.delete(entity);
	}

	//Other Methods--------------------------------------------

	public Actor isSuspicious(final Actor actor) {
		Actor result = null;
		final UserAccount userAccount = actor.getUserAccount();
		final Authority authority = userAccount.getAuthorities().iterator().next();

		switch (authority.getAuthority()) {
		case "ADMIN":
			final Administrator administrator = this.findByUseraccount(userAccount);
			administrator.setIsSuspicious(true);
			result = this.isSuspicious(administrator);
			break;
		case "CUSTOMER":
			final Customer customer = this.customerService.findByUseraccount(userAccount);
			customer.setIsSuspicious(true);
			result = this.customerService.isSuspicious(customer);

			break;
		case "REFEREE":
			final Referee referee = this.refereeService.findByUseraccount(userAccount);
			referee.setIsSuspicious(true);
			result = this.refereeService.isSuspicious(referee);
			break;
		case "SPONSOR":
			final Sponsor sponsor = this.sponsorService.findByUseraccount(userAccount);
			sponsor.setIsSuspicious(true);
			result = this.sponsorService.isSuspicious(sponsor);
			break;
		case "HANDY":
			final HandyWorker handy = this.handyWorkerService.findHandyWorkerByUserAccount(userAccount.getId());
			handy.setIsSuspicious(true);
			result = this.handyWorkerService.isSuspicious(handy);
			break;

		}

		return result;
	}

	public Administrator isSuspicious(final Administrator administrator) {
		final Administrator saved = this.administratorRepository.save(administrator);

		return saved;
	}

	public Administrator findByUseraccount(final UserAccount userAccount) {

		return this.administratorRepository.findAdministratorByUserAccount(userAccount.getId());

	}

}

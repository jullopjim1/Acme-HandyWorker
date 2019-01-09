
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
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
public class ActorService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ActorRepository			actorRepository;

	//Service-----------------------------------------------------------------------------
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private BoxService				boxService;


	public Actor create(final String authority) {
		final Actor actor = new Actor();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority(authority);
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		actor.setUserAccount(userAccount);
		actor.setIsBanned(false);
		actor.setIsSuspicious(false);
		return actor;
	}

	public Actor findOne(final int ActorId) {
		return this.actorRepository.findOne(ActorId);
	}

	Actor findActorByUsername(final String username) {
		final Actor actor = this.actorRepository.findActorByUsername(username);
		return actor;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> actors = this.actorRepository.findAll();
		Assert.notNull(actors);

		return actors;
	}

	public Actor save(final Actor actor) {

		Assert.notNull(actor);
		final Actor saved = this.actorRepository.save(actor);

		return saved;
	}

	//Other Methods---------------------------------------------------------------------

	public Collection<Actor> findSuspiciousActors() {
		return this.actorRepository.findSuspiciousActors();
	}

	public void ban(final Actor actor) {
		actor.setIsBanned(true);
		actor.getUserAccount().setEnabled(false);
		this.save(actor);
	}

	public void unban(final Actor actor) {
		actor.setIsBanned(false);
		actor.setIsSuspicious(false);
		actor.getUserAccount().setEnabled(true);
		this.save(actor);

	}

	public void update(final Actor actor) {

		Assert.notNull(actor);

		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		final Authority handy = new Authority();
		handy.setAuthority("HANDY");
		final Authority cust = new Authority();
		cust.setAuthority("CUSTOMER");
		final Authority refer = new Authority();
		refer.setAuthority("REFEREE");
		final Authority spon = new Authority();
		spon.setAuthority("SPONSOR");
		final Authority admin = new Authority();
		admin.setAuthority("ADMIN");

		if (authorities.contains(handy)) {
			HandyWorker handyWorker = null;
			if (actor.getId() != 0)
				handyWorker = this.handyWorkerService.findOne(actor.getId());
			else {
				handyWorker = this.handyWorkerService.create();
				handyWorker.setUserAccount(actor.getUserAccount());
				handyWorker.setMake(actor.getMiddleName()); // TODO adaptar a requisitos
			}
			handyWorker.setAddress(actor.getAddress());
			handyWorker.setEmail(actor.getEmail());
			handyWorker.setIsBanned(actor.getIsBanned());
			handyWorker.setIsSuspicious(actor.getIsSuspicious());
			handyWorker.setMiddleName(actor.getMiddleName());
			handyWorker.setName(actor.getName());
			handyWorker.setPhone(actor.getPhone());
			handyWorker.setPhoto(actor.getPhoto());
			handyWorker.setSurname(actor.getSurname());

			this.handyWorkerService.save(handyWorker);
			final Actor actor1 = this.handyWorkerService.save(handyWorker);
			this.boxService.addSystemBox(actor1);
		} else if (authorities.contains(cust)) {
			Customer customer = null;
			if (actor.getId() != 0)
				customer = this.customerService.findOne(actor.getId());
			else {
				customer = this.customerService.create();
				customer.setUserAccount(actor.getUserAccount());
			}

			customer.setAddress(actor.getAddress());
			customer.setEmail(actor.getEmail());
			customer.setIsBanned(actor.getIsBanned());
			customer.setIsSuspicious(actor.getIsSuspicious());
			customer.setMiddleName(actor.getMiddleName());
			customer.setName(actor.getName());
			customer.setPhone(actor.getPhone());
			customer.setPhoto(actor.getPhoto());
			customer.setSurname(actor.getSurname());

			this.customerService.save(customer);
			final Actor actor1 = this.customerService.save(customer);
			this.boxService.addSystemBox(actor1);
		} else if (authorities.contains(refer)) {
			Referee referee = null;
			if (actor.getId() != 0)
				referee = this.refereeService.findOne(actor.getId());
			else {
				referee = this.refereeService.create();
				referee.setUserAccount(actor.getUserAccount());
			}
			referee.setAddress(actor.getAddress());
			referee.setEmail(actor.getEmail());
			referee.setIsBanned(actor.getIsBanned());
			referee.setIsSuspicious(actor.getIsSuspicious());
			referee.setMiddleName(actor.getMiddleName());
			referee.setName(actor.getName());
			referee.setPhone(actor.getPhone());
			referee.setPhoto(actor.getPhoto());
			referee.setSurname(actor.getSurname());

			this.refereeService.save(referee);
			final Actor actor1 = this.refereeService.save(referee);
			this.boxService.addSystemBox(actor1);

		} else if (authorities.contains(spon)) {

			Sponsor sponsor = null;
			if (actor.getId() != 0)
				sponsor = this.sponsorService.findOne(actor.getId());
			else {
				sponsor = this.sponsorService.create();
				sponsor.setUserAccount(actor.getUserAccount());
			}
			sponsor.setAddress(actor.getAddress());
			sponsor.setEmail(actor.getEmail());
			sponsor.setIsBanned(actor.getIsBanned());
			sponsor.setIsSuspicious(actor.getIsSuspicious());
			sponsor.setMiddleName(actor.getMiddleName());
			sponsor.setName(actor.getName());
			sponsor.setPhone(actor.getPhone());
			sponsor.setPhoto(actor.getPhoto());
			sponsor.setSurname(actor.getSurname());

			final Actor actor1 = this.sponsorService.save(sponsor);
			this.boxService.addSystemBox(actor1);

		} else if (authorities.contains(admin)) {
			Administrator administrator = null;
			if (actor.getId() != 0)
				administrator = this.administratorService.findOne(actor.getId());
			else {
				administrator = this.administratorService.create();
				administrator.setUserAccount(actor.getUserAccount());
			}

			administrator.setSurname(actor.getSurname());
			administrator.setAddress(actor.getAddress());
			administrator.setEmail(actor.getEmail());
			administrator.setIsBanned(actor.getIsBanned());
			administrator.setIsSuspicious(actor.getIsSuspicious());
			administrator.setMiddleName(actor.getMiddleName());
			administrator.setName(actor.getName());
			administrator.setPhone(actor.getPhone());
			administrator.setPhoto(actor.getPhoto());

			final Actor actor1 = this.administratorService.save(administrator);
			this.boxService.addSystemBox(actor1);
		}

	}

}

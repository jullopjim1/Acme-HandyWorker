
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.Sponsor;
import repositories.ActorRepository;
import security.Authority;
import security.UserAccount;

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
			final HandyWorker handyWorker = this.handyWorkerService.findOne(actor.getId());

			handyWorker.setAddress(actor.getAddress());
			handyWorker.setEmail(actor.getEmail());
			handyWorker.setIsBanned(actor.getIsBanned());
			handyWorker.setIsSuspicious(actor.getIsSuspicious());
			handyWorker.setMiddleName(actor.getMiddleName());
			handyWorker.setName(actor.getName());
			handyWorker.setPhone(actor.getPhone());
			handyWorker.setPhoto(actor.getPhoto());

			this.handyWorkerService.save(handyWorker);
		} else if (authorities.contains(cust)) {
			final Customer customer = this.customerService.findOne(actor.getId());

			customer.setAddress(actor.getAddress());
			customer.setEmail(actor.getEmail());
			customer.setIsBanned(actor.getIsBanned());
			customer.setIsSuspicious(actor.getIsSuspicious());
			customer.setMiddleName(actor.getMiddleName());
			customer.setName(actor.getName());
			customer.setPhone(actor.getPhone());
			customer.setPhoto(actor.getPhoto());

			this.customerService.save(customer);
		} else if (authorities.contains(refer)) {
			final Referee referee = this.refereeService.findOne(actor.getId());

			referee.setAddress(actor.getAddress());
			referee.setEmail(actor.getEmail());
			referee.setIsBanned(actor.getIsBanned());
			referee.setIsSuspicious(actor.getIsSuspicious());
			referee.setMiddleName(actor.getMiddleName());
			referee.setName(actor.getName());
			referee.setPhone(actor.getPhone());
			referee.setPhoto(actor.getPhoto());

			this.refereeService.save(referee);
		} else if (authorities.contains(spon)) {
			final Sponsor sponsor = this.sponsorService.findOne(actor.getId());

			sponsor.setAddress(actor.getAddress());
			sponsor.setEmail(actor.getEmail());
			sponsor.setIsBanned(actor.getIsBanned());
			sponsor.setIsSuspicious(actor.getIsSuspicious());
			sponsor.setMiddleName(actor.getMiddleName());
			sponsor.setName(actor.getName());
			sponsor.setPhone(actor.getPhone());
			sponsor.setPhoto(actor.getPhoto());

			this.sponsorService.save(sponsor);
		} else if (authorities.contains(admin)) {
			final Administrator administrator = this.administratorService.findOne(actor.getId());

			administrator.setAddress(actor.getAddress());
			administrator.setEmail(actor.getEmail());
			administrator.setIsBanned(actor.getIsBanned());
			administrator.setIsSuspicious(actor.getIsSuspicious());
			administrator.setMiddleName(actor.getMiddleName());
			administrator.setName(actor.getName());
			administrator.setPhone(actor.getPhone());
			administrator.setPhoto(actor.getPhoto());

			this.administratorService.save(administrator);
		}

	}

}


package services;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	// Repository-------------------------------------------------------------------------

	@Autowired
	private EndorserRepository	endorserRepository;

	// Services---------------------------------------------------------------------------
	@Autowired
	private EndorsementService	endorsementService;

	@Autowired
	private ActorService		actorService;


	// Constructor------------------------------------------------------------------------

	public EndorserService() {
		super();
	}

	// Simple
	// CRUD------------------------------------------------------------------------

	// TODO create endorser
	// public Endorser create() {
	// final Endorser endorser = new Endorser();
	//
	// return endorser;
	//
	// }

	public List<Endorser> findAll() {
		return this.endorserRepository.findAll();
	}

	public Endorser findOne(final Integer endorserId) {
		return this.endorserRepository.findOne(endorserId);
	}

	// Other
	// Methods---------------------------------------------------------------------------
	public Endorser findEndorserByUseraccount(final UserAccount userAccount) {
		final Endorser endorser = this.endorserRepository.findEndorserByUseraccount(userAccount);

		return endorser;

	}

	public Endorser updateEndorser(final Endorser endorser) {

		final double totalScore = this.endorsementService.calculateScoreByEndorser(endorser.getId());

		endorser.setScore(totalScore);

		final Endorser saved = this.endorserRepository.save(endorser);

		return saved;

	}

	public void updateScoreEndorsers() {
		final Collection<Endorser> endorsers = new LinkedList<>();

		for (final Endorser endorser : this.findAll())
			endorsers.add(this.updateEndorser(endorser));

		this.endorserRepository.save(endorsers);

	}
	public Collection<Endorser> findEndorsees() {
		this.checkPrincipal();
		final Endorser endorser = this.findEndorserByUseraccount(LoginService.getPrincipal());
		Collection<Endorser> endorsees;
		final Authority handyAuthority = new Authority();
		handyAuthority.setAuthority("HANDY");
		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("CUSTOMER");

		if (endorser.getUserAccount().getAuthorities().contains(customerAuthority))
			endorsees = this.endorserRepository.findEndorseesByCustomer(endorser.getId());
		else
			endorsees = this.endorserRepository.findEndorseesByHandyWorker(endorser.getId());

		return endorsees;
	}

	private void checkPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final Authority handyAuthority = new Authority();
		handyAuthority.setAuthority("HANDY");

		final Authority customerAuthority = new Authority();
		customerAuthority.setAuthority("CUSTOMER");

		final Authority authority = new Authority();
		authority.setAuthority("ENDORSER");

		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority) || actor.getUserAccount().getAuthorities().contains(customerAuthority) || actor.getUserAccount().getAuthorities().contains(handyAuthority),
			"Solo los endorser pueden realizar endorsement");

	}

}

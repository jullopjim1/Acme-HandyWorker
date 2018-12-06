
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorserRepository;
import security.UserAccount;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	// Repository-------------------------------------------------------------------------

	@Autowired
	private EndorserRepository endorserRepository;

	// Services---------------------------------------------------------------------------
	@Autowired
	private EndorsementService endorsementService;

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

}

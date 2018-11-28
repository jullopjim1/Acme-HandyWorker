
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Configuration;
import domain.Endorsement;
import domain.Endorser;

@Service
@Transactional
public class EndorsementService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private EndorsementRepository	endorsementRepository;

	//Services---------------------------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private EndorserService			endorserService;


	//Constructor------------------------------------------------------------------------

	public EndorsementService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Endorsement create() {
		final Endorsement endorsement = new Endorsement();

		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Endorser endorser = this.endorserService.findEndorserByUseraccount(userAccount);

		this.checkPrincipal();

		final Date moment = new Date();

		endorsement.setMoment(moment);
		endorsement.setComments("");
		endorsement.setScore(0.0);

		endorsement.setEndorser(endorser);

		return endorsement;

	}

	public List<Endorsement> findAll() {
		return this.endorsementRepository.findAll();
	}

	public Endorsement findOne(final Integer endorsementId) {
		return this.endorsementRepository.findOne(endorsementId);
	}
	public Endorsement save(final Endorsement endorsement) {

		final double score = this.calculateScore(endorsement);
		//System.out.println(endorsement + " --- " + score);
		endorsement.setScore(score);

		Assert.notNull(endorsement.getEndorsee(), "El destinatario de la endorsement no puede ser nulo");

		final Endorsement saved = this.endorsementRepository.save(endorsement);

		return saved;
	}

	public void delete(final Endorsement entity) {
		this.endorsementRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

	public double calculateScore(final Endorsement endorsement) {
		double score = 0.0;

		final String comments = endorsement.getComments().toUpperCase();

		final Configuration configuration = this.configurationService.findAll().iterator().next();

		final Collection<String> positiveWords = configuration.getPositiveWords();
		final Collection<String> negativeWords = configuration.getNegativeWords();

		final double total = positiveWords.size() + negativeWords.size();

		double p = 0.0;
		for (final String positive : positiveWords)
			if (comments.contains(positive.toUpperCase()))
				p++;

		double n = 0.0;
		for (final String negative : negativeWords)
			if (comments.contains(negative.toUpperCase()))
				n++;

		score = (p - n) / total;

		return score;
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

	public double calculateScoreByEndorser(final int endorserId) {
		return this.endorsementRepository.calculateScoreByEndorser(endorserId);
	}

}

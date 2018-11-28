
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorshipRepository;
import security.LoginService;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorshipService {

	// Managed repository ------------------------------------------------------------------
	@Autowired
	private SponsorshipRepository	sponsorshipRepository;

	@Autowired
	private SponsorService			sponsorService;


	//Constructor----------------------------------------------------------------------------

	public SponsorshipService() {
		super();
	}
	// Simple CRUD methods -------------------------------------------------------------------
	public Sponsorship create(final int SponsorId) {
		final Sponsorship sponsorship = new Sponsorship();
		sponsorship.setSponsor(this.sponsorService.findOne(SponsorId));
		sponsorship.setBanner("");
		sponsorship.setLink("");
		return sponsorship;
	}

	public Collection<Sponsorship> findAll() {
		Collection<Sponsorship> sponsorships;

		sponsorships = this.sponsorshipRepository.findAll();
		Assert.notNull(sponsorships);

		return sponsorships;
	}

	public Sponsorship findOne(final int SponsorshipId) {
		Sponsorship sponsorship;
		sponsorship = this.sponsorshipRepository.findOne(SponsorshipId);
		Assert.notNull(sponsorship);

		return sponsorship;
	}

	public Sponsorship save(final Sponsorship sponsorship) {
		Assert.notNull(sponsorship);
		this.checkPrincipal(sponsorship);
		Sponsorship result;

		result = this.sponsorshipRepository.save(sponsorship);

		return result;
	}

	public void delete(final Sponsorship sponsorship) {
		this.checkPrincipal(sponsorship);
		Assert.notNull(sponsorship);
		this.sponsorshipRepository.delete(sponsorship);
	}

	// Other bussines methods ------------------------------ (Otras reglas de negocio, como por ejemplo findRegisteredUser())
	public Boolean checkPrincipal(final Sponsorship sponsorship) {
		final Sponsor sponsor = sponsorship.getSponsor();
		Assert.isTrue(sponsor.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}
}


package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository ------------------------------------------------------------------
	@Autowired
	private SponsorRepository	sponsorRepository;


	//Constructor----------------------------------------------------------------------------

	public SponsorService() {
		super();
	}

	// Simple CRUD methods ------------------------------ (Operaciones básicas, pueden tener restricciones según los requisitos)
	public Sponsor create() {
		Sponsor sponsor;

		sponsor = new Sponsor();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority("SPONSOR");
		authorities.add(a);
		userAccount.setAuthorities(authorities);

		sponsor.setIsBanned(false);
		sponsor.setIsSuspicious(false);

		return sponsor;
	}
	public Collection<Sponsor> findAll() {
		Collection<Sponsor> Sponsors;

		Sponsors = this.sponsorRepository.findAll();
		Assert.notNull(Sponsors);

		return Sponsors;
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor sponsor;
		sponsor = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(sponsor);

		return sponsor;
	}

	public Sponsor save(final Sponsor sponsor) {
		Sponsor result;
		Assert.notNull(sponsor.getUserAccount());

		result = this.sponsorRepository.save(sponsor);

		Assert.notNull(result);
		return result;
	}

	public void delete(final Sponsor sponsor) {

		Assert.notNull(sponsor);

		this.sponsorRepository.delete(sponsor);
	}

	// Other bussines methods ------------------------------ (Otras reglas de negocio, como por ejemplo findRegisteredUser())

}

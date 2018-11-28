
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CreditCardService;
import services.SponsorService;
import services.SponsorshipService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private CreditCardService	creditCardService;


	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final Sponsorship sponsorship;
		this.authenticate("sponsor1");
		final int sponsorId = this.getEntityId("sponsor1");
		final int creditCardId = this.getEntityId("creditcard1");

		try {
			sponsorship = this.sponsorshipService.create(sponsorId);
			sponsorship.setBanner("http://banner9.com");
			sponsorship.setLink("http://sponsorship7.com");
			final CreditCard cc = this.creditCardService.findOne(creditCardId);
			final Sponsor x = this.sponsorService.findOne(sponsorId);
			sponsorship.setCreditCard(cc);
			sponsorship.setSponsor(x);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int sponsorshipId = this.getEntityId("sponsorship1");

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			Assert.notNull(sponsorship);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final Sponsorship sponsorship, saved;

		final Collection<Sponsorship> sponsorships;

		this.authenticate("sponsor1");
		final int sponsorId = this.getEntityId("sponsor1");
		final int creditCardId = this.getEntityId("creditcard1");

		try {
			sponsorship = this.sponsorshipService.create(sponsorId);
			sponsorship.setBanner("http://banner9.com");
			sponsorship.setLink("http://sponsorship7.com");
			final CreditCard cc = this.creditCardService.findOne(creditCardId);
			final Sponsor x = this.sponsorService.findOne(sponsorId);
			sponsorship.setCreditCard(cc);
			sponsorship.setSponsor(x);

			saved = this.sponsorshipService.save(sponsorship);

			sponsorships = this.sponsorshipService.findAll();
			Assert.isTrue(sponsorships.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final int sponsorshipId = this.getEntityId("sponsorship1");
		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAll();
			Assert.isTrue(sponsorships.contains(sponsorship));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		final int sponsorshipId = this.getEntityId("sponsorship1");

		try {
			final Sponsorship sponsorship = this.sponsorshipService.findOne(sponsorshipId);
			this.sponsorshipService.delete(sponsorship);
			final Collection<Sponsorship> sponsorships = this.sponsorshipService.findAll();
			Assert.notNull(sponsorships);
			Assert.isTrue(!sponsorships.contains(sponsorship));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
}

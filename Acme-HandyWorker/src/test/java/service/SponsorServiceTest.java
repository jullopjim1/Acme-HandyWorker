
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.SponsorService;
import utilities.AbstractTest;
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private SponsorService	sponsorService;


	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final Sponsor sponsor;

		try {
			sponsor = this.sponsorService.create();
			sponsor.setAddress("111");
			sponsor.setEmail("emailSponsor1@gmail.com");
			sponsor.setMiddleName("middle1");
			sponsor.setName("sponsor1");
			sponsor.setPhone("650190444");
			sponsor.setSurname("ww");
			sponsor.setPhoto("http://www.photo5.com");

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int sponsorId = this.getEntityId("sponsor1");

		try {
			final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
			Assert.notNull(sponsor);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final Sponsor sponsor, saved;

		final Collection<Sponsor> sponsors;

		try {
			sponsor = this.sponsorService.create();
			sponsor.setAddress("111");
			sponsor.setEmail("emailSponsor1@gmail.com");
			sponsor.setMiddleName("middle1");
			sponsor.setName("sponsor1");
			sponsor.setPhone("650190444");
			sponsor.setSurname("ww");
			sponsor.setPhoto("http://www.photo5.com");

			saved = this.sponsorService.save(sponsor);

			sponsors = this.sponsorService.findAll();
			Assert.isTrue(sponsors.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final int sponsorId = this.getEntityId("sponsor1");
		try {
			final Sponsor sponsor = this.sponsorService.findOne(sponsorId);
			final Collection<Sponsor> sponsors = this.sponsorService.findAll();
			Assert.isTrue(sponsors.contains(sponsor));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

}


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


	//Test-------------------------------------------------------------

	@Test
	public void testSponsor() {
		System.out.println("------Test Sponsor------");
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
			Assert.notNull(saved);
			sponsors = this.sponsorService.findAll();

			Assert.isTrue(sponsors.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}
}

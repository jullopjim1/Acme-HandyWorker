
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
import services.SponsorshipService;
import utilities.AbstractTest;
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


	//@Autowired
	//private CreditCardService	creditCardService;

	//Test-------------------------------------------------------------

	@Test
	public void testSponsorship() {
		System.out.println("------Test Warranty------");
		final Sponsorship sponsorship, saved;
		final Collection<Sponsorship> sponsorships;
		this.authenticate("sponsor1");
		final int sponsorId = this.getEntityId("sponsor1");
		final Sponsor x = this.sponsorService.findOne(sponsorId);
		sponsorship = this.sponsorshipService.create(sponsorId);
		sponsorship.setBanner("http://banner9.com");
		sponsorship.setLink("http://sponsorship7.com");
		//final CreditCard cc = new CreditCard();
		//cc.setBrandName("VISA");
		//cc.setCVVCode(555);
		//cc.setExpirationMonth(10);
		//cc.setExpirationYear(2020);
		//cc.setHolderName("anotonio");
		//cc.setNumber("12394012957125");
		//this.creditCardService.save(cc);
		//sponsorship.setCreditCard(cc);

		saved = this.sponsorshipService.save(sponsorship);
		Assert.notNull(saved);
		sponsorships = this.sponsorshipService.findAll();
		Assert.isTrue(sponsorship.getSponsor().equals(x));
		Assert.isTrue(sponsorships.contains(saved));
	}
}

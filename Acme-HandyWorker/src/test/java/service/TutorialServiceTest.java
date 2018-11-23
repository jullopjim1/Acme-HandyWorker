
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Sponsorship;
import domain.Tutorial;
import services.SectionService;
import services.SponsorshipService;
import services.TutorialService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Services------------------------------------------------------------
	@Autowired
	private TutorialService		tutorialService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private SectionService		sectionService;


	//Test
	@Test
	public void testTutorial() {
		System.out.println("------Test Tutorial------");

		this.authenticate("handyWorker1");

		final Tutorial tutorial, saved;

		final int handyWorkerId = this.getEntityId("handyWorker1");

		final int sponsorshipId = this.getEntityId("sponsorship2");
		final Sponsorship sp = this.sponsorshipService.findOne(sponsorshipId);

		tutorial = this.tutorialService.create(handyWorkerId);
		tutorial.setPictures("http://www.pictures1.com");
		tutorial.setSponsorship(sp);
		tutorial.setSummary("summary");
		tutorial.setTitle("tutorial1");
		Assert.notNull(tutorial);

		saved = this.tutorialService.save(tutorial);

		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(saved));

	}
}

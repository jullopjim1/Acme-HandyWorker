
package service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.SectionService;
import services.SponsorshipService;
import services.TutorialService;
import utilities.AbstractTest;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

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
		final Tutorial tutorial, saved;
		final Collection<Tutorial> tutorials;

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");
		tutorial = this.tutorialService.create(handyWorkerId);
		final Collection<Section> s = new ArrayList<>();
		final Section uno = new Section();
		uno.setPictures("http://www.pictures1.com");
		uno.setPosition(1);
		uno.setText("aaaa");
		uno.setTitle("section1");

		final Section dos = this.sectionService.save(uno);
		s.add(dos);
		tutorial.setSections(s);
		tutorial.setPictures("http://www.pictures1.com");
		final int sponsorshipId = this.getEntityId("sponsorship2");
		final Sponsorship sp = this.sponsorshipService.findOne(sponsorshipId);
		tutorial.setSponsorship(sp);
		Assert.notNull(tutorial.getSponsorship());
		tutorial.setSummary("summary");
		tutorial.setTitle("tutorial1");

		Assert.notNull(tutorial);
		saved = this.tutorialService.save(tutorial);

		tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(saved));

	}
}

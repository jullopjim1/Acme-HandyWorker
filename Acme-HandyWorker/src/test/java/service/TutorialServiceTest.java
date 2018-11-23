
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
import domain.Tutorial;

;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class TutorialServiceTest extends AbstractTest {

	//Service ------------------------------ 
	@Autowired
	private TutorialService		tutorialService;
	@Autowired
	private SponsorshipService	sponsorshipService;
	@Autowired
	private SectionService		sectionService;


	//Test
	@Test
	public void testCreate() {
		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");
		final int sponsorshipId = this.getEntityId("sponsorship1");
		try {
			final Tutorial tutorial = this.tutorialService.create(handyWorkerId);
			tutorial.setPictures("http://photo1.com");
			tutorial.setSponsorship(this.sponsorshipService.findOne(sponsorshipId));
			tutorial.setSummary("aaa");
			tutorial.setTitle("aa");
			this.tutorialService.save(tutorial);
			final int sectionId = this.getEntityId("section1");
			final Section g = this.sectionService.findOne(sectionId);
			this.sectionService.save(g);
			tutorial.getSections().add(g);

			Assert.notNull(tutorial);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		final Tutorial tutorial, saved, saved1;

		final Collection<Tutorial> tutorials;

		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");
		final int sponsorshipId = this.getEntityId("sponsorship1");
		tutorial = this.tutorialService.create(handyWorkerId);
		tutorial.setPictures("http://photo1.com");
		tutorial.setSummary("aaa");
		tutorial.setTitle("aa");
		tutorial.setSponsorship(this.sponsorshipService.findOne(sponsorshipId));
		saved = this.tutorialService.save(tutorial);

		final int sectionId = this.getEntityId("section1");
		final Section g = this.sectionService.findOne(sectionId);
		this.sectionService.save(g);
		tutorial.getSections().add(g);

		saved1 = this.tutorialService.save(saved);
		tutorials = this.tutorialService.findAll();
		tutorials.add(saved1);
		Assert.isTrue(tutorials.contains(saved1));

	}
	@Test
	public void testFindOne() {

		final int tutorialId = this.getEntityId("tutorial1");

		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);
			Assert.notNull(tutorial);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		final int tutorial1 = this.getEntityId("tutorial1");
		final Tutorial tutorial = this.tutorialService.findOne(tutorial1);
		final Collection<Tutorial> tutorials = this.tutorialService.findAll();
		Assert.isTrue(tutorials.contains(tutorial));

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		final int tutorialId = this.getEntityId("tutorial1");

		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

			final Collection<Tutorial> tutorials = new ArrayList<>(this.tutorialService.findAll());
			Assert.isTrue(!tutorials.contains(tutorial));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

}

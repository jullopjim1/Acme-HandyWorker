
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

import services.SponsorshipService;
import services.TutorialService;
import utilities.AbstractTest;
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


	//Test
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");
		final int sponsorshipId = this.getEntityId("sponsorship1");
		try {
			final Tutorial tutorial = this.tutorialService.create(handyWorkerId);
			tutorial.setPictures("http://photo1.com");
			tutorial.setSponsorship(this.sponsorshipService.findOne(sponsorshipId));
			tutorial.setSummary("aaa");
			tutorial.setTitle("aa");

			Assert.notNull(tutorial);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final Tutorial tutorial, saved;

		final Collection<Tutorial> tutorials;

		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");
		final int sponsorshipId = this.getEntityId("sponsorship1");

		try {
			tutorial = this.tutorialService.create(handyWorkerId);
			tutorial.setPictures("http://photo1.com");
			tutorial.setSummary("aaa");
			tutorial.setTitle("aa");
			tutorial.setSponsorship(this.sponsorshipService.findOne(sponsorshipId));
			saved = this.tutorialService.save(tutorial);

			tutorials = this.tutorialService.findAll();
			Assert.isTrue(tutorials.contains(saved));
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println(e.getMessage());
		}

	}
	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
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
		System.out.println("========== testFindAll() ==========");
		try {
			final int tutorial1 = this.getEntityId("tutorial1");
			final Tutorial tutorial = this.tutorialService.findOne(tutorial1);
			final Collection<Tutorial> tutorials = this.tutorialService.findAll();
			Assert.isTrue(tutorials.contains(tutorial));
			System.out.println("¡Exito!");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("handyworker1");
		final int tutorialId = this.getEntityId("tutorial1");

		try {
			final Tutorial tutorial = this.tutorialService.findOne(tutorialId);

			this.tutorialService.delete(tutorial);

			final Collection<Tutorial> tutorials = new ArrayList<>(this.tutorialService.findAll());
			Assert.isTrue(!tutorials.contains(tutorial));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}

}

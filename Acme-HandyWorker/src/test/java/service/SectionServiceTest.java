
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
import services.TutorialService;
import utilities.AbstractTest;
import domain.Section;
import domain.Tutorial;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	//Service ------------------------------
	@Autowired
	private SectionService	sectionService;

	@Autowired
	private TutorialService	tutorialService;


	//Test
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		this.authenticate("handyWorker3");
		final int handyWorkerId = this.getEntityId("handyWorker3");

		final ArrayList<Tutorial> tutorials = new ArrayList<>(this.tutorialService.findTutorialsByHandyWorkerId(handyWorkerId));
		Assert.notNull(tutorials);

		try {
			final Section section = this.sectionService.create(tutorials.get(0).getId());
			section.setPictures("http://www.photo142.com");
			section.setPosition(1);
			section.setText("aaaa");
			section.setTitle("eee");

			Assert.notNull(section);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		Section saved;

		this.authenticate("handyWorker3");
		final int handyWorkerId = this.getEntityId("handyWorker3");

		final ArrayList<Tutorial> tutorials = new ArrayList<>(this.tutorialService.findTutorialsByHandyWorkerId(handyWorkerId));
		Assert.notNull(tutorials);

		try {
			final Section section = this.sectionService.create(tutorials.get(0).getId());
			section.setPictures("http://www.photo142.com");
			section.setPosition(1);
			section.setText("aaaa");
			section.setTitle("eee");

			Assert.notNull(section);

			saved = this.sectionService.save(section);

			final Collection<Section> sections = this.sectionService.findAll();
			Assert.isTrue(sections.contains(saved));
			System.out.println("¡Exito!");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int sectionId = this.getEntityId("section1");

		try {
			final Section section = this.sectionService.findOne(sectionId);
			Assert.notNull(section);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");
		final int section1 = this.getEntityId("section1");
		final Section section = this.sectionService.findOne(section1);
		try {
			final Collection<Section> sections = this.sectionService.findAll();
			Assert.isTrue(sections.contains(section));
			System.out.println("¡Exito!");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		this.authenticate("handyWorker3");

		final int handyWorkerId = this.getEntityId("handyWorker3");

		try {
			final ArrayList<Tutorial> tutorials = new ArrayList<>(this.tutorialService.findTutorialsByHandyWorkerId(handyWorkerId));
			Assert.notNull(tutorials);

			final ArrayList<Section> sections = new ArrayList<>(this.sectionService.findSectionByTutorialId(tutorials.get(0).getId()));
			Assert.notNull(sections);

			final Section section = sections.get(0);

			this.sectionService.delete(section);

			final Collection<Section> sectionsAll = this.sectionService.findAll();
			Assert.isTrue(!sectionsAll.contains(section));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

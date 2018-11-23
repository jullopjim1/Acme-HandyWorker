
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
import utilities.AbstractTest;
import domain.Section;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SectionServiceTest extends AbstractTest {

	//Service ------------------------------ 
	@Autowired
	private SectionService	sectionService;


	//Test
	@Test
	public void testCreate() {

		try {
			final Section section = this.sectionService.create();
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
		final Section section;
		Section saved;

		final Collection<Section> sections;

		section = this.sectionService.create();
		section.setPictures("http://www.photo152.com");
		section.setPosition(1);
		section.setText("aaaae");
		section.setTitle("eeea");

		saved = this.sectionService.save(section);

		sections = this.sectionService.findAll();
		sections.add(saved);
		Assert.isTrue(sections.contains(saved));

	}
	@Test
	public void testFindOne() {

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
		final int section1 = this.getEntityId("section1");
		final Section section = this.sectionService.findOne(section1);
		final Collection<Section> sections = this.sectionService.findAll();
		Assert.isTrue(sections.contains(section));

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		final int sectionId = this.getEntityId("section1");

		try {
			final Section section = this.sectionService.findOne(sectionId);

			final Collection<Section> sections = new ArrayList<>(this.sectionService.findAll());
			Assert.isTrue(!sections.contains(section));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

}

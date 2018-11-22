
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

import domain.Curriculum;
import domain.PersonalRecord;
import services.CurriculumService;
import services.PersonalRecordService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private PersonalRecordService	personalRecordService;

	@Autowired
	private CurriculumService		curriculumService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");

		try {
			final Curriculum curriculum = this.curriculumService.create(handyWorkerId);
			Assert.notNull(curriculum);

			final PersonalRecord personalRecord = this.personalRecordService.create(curriculum.getId());
			personalRecord.setLink("http://jajajajajja.com");
			Assert.notNull(personalRecord);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");

		try {
			final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);
			Assert.notNull(curriculum);

			final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculum.getId());
			Assert.notNull(personalRecord);

			Assert.isTrue(personalRecord.getCurriculum().getId() == curriculum.getId());

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");

		try {
			final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);
			Assert.notNull(curriculum);

			final Collection<PersonalRecord> personalRecords = new ArrayList<>(this.personalRecordService.findAll());
			final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculum.getId());
			Assert.notNull(personalRecord);
			Assert.isTrue(personalRecords.contains(personalRecord));

			for (final PersonalRecord personalRecord1 : personalRecords)
				System.out.println(personalRecord1.getFullName());

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();
	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");

		try {
			final Curriculum curriculum = this.curriculumService.create(handyWorkerId);
			Assert.notNull(curriculum.getHandyWorker());
			Assert.notNull(curriculum.getTicker());
			final Curriculum saved = this.curriculumService.save(curriculum);

			final PersonalRecord personalRecord = this.personalRecordService.create(saved.getId());
			personalRecord.setLink("http://jajajajajja.com");
			Assert.notNull(personalRecord);

			final PersonalRecord savedP = this.personalRecordService.save(personalRecord);

			final Collection<PersonalRecord> personalRecords = new ArrayList<>(this.personalRecordService.findAll());
			Assert.isTrue(personalRecords.contains(savedP));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");

		try {
			final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);
			Assert.notNull(curriculum);

			final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculum.getId());
			Assert.notNull(personalRecord);

			this.personalRecordService.delete(personalRecord);

			final Collection<PersonalRecord> personalRecords = new ArrayList<>(this.personalRecordService.findAll());
			Assert.isTrue(!personalRecords.contains(personalRecord));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

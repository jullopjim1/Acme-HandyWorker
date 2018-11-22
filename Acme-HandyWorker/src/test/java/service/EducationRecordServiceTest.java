
package service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.EducationRecord;
import services.CurriculumService;
import services.EducationRecordService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private EducationRecordService	educationRecordService;

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
			Assert.notNull(curriculum.getHandyWorker());
			Assert.notNull(curriculum.getTicker());
			final Curriculum saved = this.curriculumService.save(curriculum);

			final EducationRecord educationRecord = this.educationRecordService.create(saved.getId());
			educationRecord.setTitle("hola");
			educationRecord.setInstitution("Hola");
			educationRecord.setStartMoment(new Date());

			Assert.notNull(educationRecord);

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

			final EducationRecord educationRecord = this.educationRecordService.findEducationRecordByCurriculumId(curriculum.getId());
			Assert.notNull(educationRecord);

			Assert.isTrue(educationRecord.getCurriculum().getId() == curriculum.getId());

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

			final Collection<EducationRecord> educationRecords = new ArrayList<>(this.educationRecordService.findAll());
			final EducationRecord educationRecord = this.educationRecordService.findEducationRecordByCurriculumId(curriculum.getId());
			Assert.notNull(educationRecord);
			Assert.isTrue(educationRecords.contains(educationRecord));

			for (final EducationRecord educationRecord1 : educationRecords)
				System.out.println(educationRecord1.getTitle());

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

			final EducationRecord educationRecord = this.educationRecordService.create(saved.getId());
			educationRecord.setTitle("hola");
			educationRecord.setInstitution("Hola");
			educationRecord.setStartMoment(new Date());
			Assert.notNull(educationRecord);

			final EducationRecord savedE = this.educationRecordService.save(educationRecord);

			final Collection<EducationRecord> educationRecords = new ArrayList<>(this.educationRecordService.findAll());
			Assert.isTrue(educationRecords.contains(savedE));

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

			final EducationRecord educationRecord = this.educationRecordService.findEducationRecordByCurriculumId(curriculum.getId());
			Assert.notNull(educationRecord);

			this.educationRecordService.delete(educationRecord);

			final Collection<EducationRecord> educationRecords = new ArrayList<>(this.educationRecordService.findAll());
			Assert.isTrue(!educationRecords.contains(educationRecord));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

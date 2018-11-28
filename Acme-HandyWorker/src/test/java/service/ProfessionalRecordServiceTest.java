
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
import domain.ProfessionalRecord;
import services.CurriculumService;
import services.ProfessionalRecordService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private CurriculumService			curriculumService;


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

			final ProfessionalRecord professionalRecord = this.professionalRecordService.create(curriculum.getId());
			professionalRecord.setRole("hola");
			professionalRecord.setName("hola");
			professionalRecord.setStartMoment(new Date());

			Assert.notNull(professionalRecord);

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

			final ArrayList<ProfessionalRecord> professionalRecords = new ArrayList<>(this.professionalRecordService.findProfessionalRecordByCurriculumId(curriculum.getId()));
			final ProfessionalRecord professionalRecord = professionalRecords.get(0);
			Assert.notNull(professionalRecord);

			Assert.isTrue(professionalRecord.getCurriculum().getId() == curriculum.getId());

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

			final Collection<ProfessionalRecord> professionalRecordsAll = new ArrayList<>(this.professionalRecordService.findAll());
			final ArrayList<ProfessionalRecord> professionalRecords = new ArrayList<>(this.professionalRecordService.findProfessionalRecordByCurriculumId(curriculum.getId()));
			final ProfessionalRecord professionalRecord = professionalRecords.get(0);
			Assert.notNull(professionalRecord);
			Assert.isTrue(professionalRecordsAll.contains(professionalRecord));

			for (final ProfessionalRecord professionalRecord1 : professionalRecordsAll)
				System.out.println(professionalRecord1.getName());

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

			final ProfessionalRecord professionalRecord = this.professionalRecordService.create(saved.getId());
			professionalRecord.setRole("hola");
			professionalRecord.setName("hola");
			professionalRecord.setStartMoment(new Date());
			Assert.notNull(professionalRecord);

			final ProfessionalRecord savedP = this.professionalRecordService.save(professionalRecord);

			final Collection<ProfessionalRecord> professionalRecords = new ArrayList<>(this.professionalRecordService.findAll());
			Assert.isTrue(professionalRecords.contains(savedP));

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

			final ArrayList<ProfessionalRecord> professionalRecords = new ArrayList<>(this.professionalRecordService.findProfessionalRecordByCurriculumId(curriculum.getId()));
			final ProfessionalRecord professionalRecord = professionalRecords.get(0);
			Assert.notNull(professionalRecord);

			this.professionalRecordService.delete(professionalRecord);

			final Collection<ProfessionalRecord> professionalRecordsAll = new ArrayList<>(this.professionalRecordService.findAll());
			Assert.isTrue(!professionalRecordsAll.contains(professionalRecord));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

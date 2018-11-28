
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
import domain.MiscellaneousRecord;
import services.CurriculumService;
import services.MiscellaneousRecordService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

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

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create(curriculum.getId());
			miscellaneousRecord.setTitle("Hola");
			miscellaneousRecord.setLink("http://ssssss.com");

			Assert.notNull(miscellaneousRecord);

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

			final ArrayList<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>(this.miscellaneousRecordService.findMiscellaneousRecordByCurriculumId(curriculum.getId()));
			final MiscellaneousRecord miscellaneousRecord = miscellaneousRecords.get(0);
			Assert.notNull(miscellaneousRecord);

			Assert.isTrue(miscellaneousRecord.getCurriculum().getId() == curriculum.getId());

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

			final Collection<MiscellaneousRecord> miscellaneousRecordsAll = new ArrayList<>(this.miscellaneousRecordService.findAll());
			final ArrayList<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>(this.miscellaneousRecordService.findMiscellaneousRecordByCurriculumId(curriculum.getId()));
			final MiscellaneousRecord miscellaneousRecord = miscellaneousRecords.get(0);
			Assert.notNull(miscellaneousRecord);
			Assert.isTrue(miscellaneousRecordsAll.contains(miscellaneousRecord));

			for (final MiscellaneousRecord miscellaneousRecord1 : miscellaneousRecordsAll)
				System.out.println(miscellaneousRecord1.getTitle());

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

			final MiscellaneousRecord miscellaneousRecord = this.miscellaneousRecordService.create(saved.getId());
			miscellaneousRecord.setTitle("Hola");
			miscellaneousRecord.setLink("http://ssssss.com");
			Assert.notNull(miscellaneousRecord);

			final MiscellaneousRecord savedE = this.miscellaneousRecordService.save(miscellaneousRecord);

			final Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>(this.miscellaneousRecordService.findAll());
			Assert.isTrue(miscellaneousRecords.contains(savedE));

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

			final ArrayList<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>(this.miscellaneousRecordService.findMiscellaneousRecordByCurriculumId(curriculum.getId()));
			final MiscellaneousRecord miscellaneousRecord = miscellaneousRecords.get(0);
			Assert.notNull(miscellaneousRecord);

			this.miscellaneousRecordService.delete(miscellaneousRecord);

			final Collection<MiscellaneousRecord> miscellaneousRecordsAll = new ArrayList<>(this.miscellaneousRecordService.findAll());
			Assert.isTrue(!miscellaneousRecordsAll.contains(miscellaneousRecord));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

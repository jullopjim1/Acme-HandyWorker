
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
import domain.EndorserRecord;
import services.CurriculumService;
import services.EndorserRecordService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserRecordServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private EndorserRecordService	endorserRecordService;

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

			final EndorserRecord endorserRecord = this.endorserRecordService.create(curriculum.getId());
			endorserRecord.setEmail("aaaa@aaaa.com");
			endorserRecord.setFullName("Hola");
			endorserRecord.setLink("http://ssssss.com");
			endorserRecord.setPhone("666666666");

			Assert.notNull(endorserRecord);

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

			final EndorserRecord endorserRecord = this.endorserRecordService.findEndorserRecordByCurriculumId(curriculum.getId());
			Assert.notNull(endorserRecord);

			Assert.isTrue(endorserRecord.getCurriculum().getId() == curriculum.getId());

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

			final Collection<EndorserRecord> endorserRecords = new ArrayList<>(this.endorserRecordService.findAll());
			final EndorserRecord endorserRecord = this.endorserRecordService.findEndorserRecordByCurriculumId(curriculum.getId());
			Assert.notNull(endorserRecord);
			Assert.isTrue(endorserRecords.contains(endorserRecord));

			for (final EndorserRecord endorserRecord1 : endorserRecords)
				System.out.println(endorserRecord1.getFullName());

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

			final EndorserRecord endorserRecord = this.endorserRecordService.create(saved.getId());
			endorserRecord.setEmail("aaaa@aaaa.com");
			endorserRecord.setFullName("Hola");
			endorserRecord.setLink("http://ssssss.com");
			endorserRecord.setPhone("666666666");
			Assert.notNull(endorserRecord);

			final EndorserRecord savedE = this.endorserRecordService.save(endorserRecord);

			final Collection<EndorserRecord> endorserRecords = new ArrayList<>(this.endorserRecordService.findAll());
			Assert.isTrue(endorserRecords.contains(savedE));

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

			final EndorserRecord endorserRecord = this.endorserRecordService.findEndorserRecordByCurriculumId(curriculum.getId());
			Assert.notNull(endorserRecord);

			this.endorserRecordService.delete(endorserRecord);

			final Collection<EndorserRecord> endorserRecords = new ArrayList<>(this.endorserRecordService.findAll());
			Assert.isTrue(!endorserRecords.contains(endorserRecord));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

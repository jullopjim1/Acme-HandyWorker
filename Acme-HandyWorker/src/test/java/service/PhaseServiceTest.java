
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

import services.PhaseService;
import utilities.AbstractTest;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private PhaseService	phaseService;


	//Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		try {
			final Phase phase = this.phaseService.create(fixUpTaskId);
			phase.setTitle("Hola");
			phase.setDescription("hijos de putaaaaaaaaaaa este test es la polla");
			phase.setStartMoment(new Date("2016/01/02 12:12"));
			phase.setEndMoment(new Date("2018/01/02 12:12"));
			Assert.notNull(phase);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int phaseId = this.getEntityId("phase1");

		try {
			final Phase phase = this.phaseService.findOne(phaseId);
			Assert.notNull(phase);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		final int phaseId = this.getEntityId("phase1");

		try {
			final Collection<Phase> phases = new ArrayList<>(this.phaseService.findAll());
			final Phase phase = this.phaseService.findOne(phaseId);
			Assert.notNull(phase);
			Assert.isTrue(phases.contains(phase));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		try {
			final Phase phase = this.phaseService.create(fixUpTaskId);
			phase.setTitle("Hola");
			phase.setDescription("hijos de putaaaaaaaaaaa este test es la polla");
			phase.setStartMoment(new Date("2016/01/02 12:12"));
			phase.setEndMoment(new Date("2018/01/02 12:12"));
			Assert.notNull(phase);

			final Phase saved = this.phaseService.save(phase);
			final Collection<Phase> phases = new ArrayList<>(this.phaseService.findAll());
			Assert.isTrue(phases.contains(saved));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo, " + e.getMessage() + "!");
		}
	}
	/*
	 * @Test
	 * public void testDelete() {
	 * System.out.println("========== testDelete() ==========");
	 * 
	 * this.authenticate("handyWorker2");
	 * final int handyWorkerId = this.getEntityId("handyWorker2");
	 * 
	 * try {
	 * 
	 * final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);
	 * Assert.notNull(curriculum);
	 * 
	 * this.curriculumService.delete(curriculum);
	 * 
	 * final Collection<Curriculum> curriculums = new ArrayList<>(this.curriculumService.findAll());
	 * Assert.isTrue(!curriculums.contains(curriculum));
	 * 
	 * System.out.println("¡Exito!");
	 * 
	 * } catch (final Exception e) {
	 * System.out.println("¡Fallo," + e.getMessage() + "!");
	 * }
	 * 
	 * this.unauthenticate();
	 * 
	 * }
	 */
}


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

import domain.FixUpTask;
import domain.Phase;
import services.FixUpTaskService;
import services.PhaseService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		try {
			final Phase phase = this.phaseService.create(fixUpTaskId);
			phase.setTitle("Hola");
			phase.setDescription("Description 1");
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

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");
		final ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(this.fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));
		final int fixUpTaskId = fixUpTasks.get(0).getId();

		try {
			final Phase phase = this.phaseService.create(fixUpTaskId);
			phase.setTitle("Hola");
			phase.setDescription("Description");
			phase.setStartMoment(new Date("2016/01/02 12:12"));
			phase.setEndMoment(new Date("2018/01/02 12:12"));
			Assert.notNull(phase);

			final Phase saved = this.phaseService.save(phase);
			final Collection<Phase> phases = this.phaseService.findPhasesByFixUpTaskIdActive(fixUpTaskId);
			Assert.isTrue(phases.contains(saved));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo, " + e.getMessage() + "!");
		}

		this.unauthenticate();
	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		this.authenticate("handyWorker2");
		final int handyWorkerId = this.getEntityId("handyWorker2");
		try {

			final ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(this.fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));
			final FixUpTask fixUpTask = fixUpTasks.get(0);
			final ArrayList<Phase> phases = new ArrayList<>(this.phaseService.findPhasesByFixUpTaskIdActive(fixUpTask.getId()));

			this.phaseService.delete(phases.get(0));

			final Collection<Phase> phaseAll = new ArrayList<>(this.phaseService.findPhasesByFixUpTaskIdActive(fixUpTask.getId()));
			Assert.isTrue(!phaseAll.contains(phases));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

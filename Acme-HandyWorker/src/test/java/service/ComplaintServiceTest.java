
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

import domain.Complaint;
import domain.FixUpTask;
import services.ComplaintService;
import services.FixUpTaskService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		this.authenticate("customer2");

		final int customerId = this.getEntityId("customer2");

		final ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(this.fixUpTaskService.findFixUpTaskByCustomerId(customerId));
		final FixUpTask fixUpTask = fixUpTasks.get(0);

		try {
			final Complaint complaint = this.complaintService.create(fixUpTask.getId());
			complaint.setAttachments("http://www.attachments1.com");
			complaint.setDescription("Te como la verga");
			Assert.notNull(complaint);
			System.out.println("�Exito!");

		} catch (final Exception e) {
			System.out.println("�Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int complaintId = this.getEntityId("complaint1");

		try {
			final Complaint complaint = this.complaintService.findOne(complaintId);
			Assert.notNull(complaint);
			System.out.println("�Exito!");

		} catch (final Exception e) {
			System.out.println("�Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		final int complaintId = this.getEntityId("complaint1");

		try {
			final Collection<Complaint> complaints = new ArrayList<>(this.complaintService.findAll());
			final Complaint complaint = this.complaintService.findOne(complaintId);
			Assert.notNull(complaint);
			Assert.isTrue(complaints.contains(complaint));

			System.out.println("�Exito!");

		} catch (final Exception e) {
			System.out.println("�Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		this.authenticate("customer2");

		final int customerId = this.getEntityId("customer2");

		final ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(this.fixUpTaskService.findFixUpTaskByCustomerId(customerId));
		final FixUpTask fixUpTask = fixUpTasks.get(0);

		try {
			final Complaint complaint = this.complaintService.create(fixUpTask.getId());
			complaint.setAttachments("http://www.attachments1.com");
			complaint.setDescription("Te como la verga");
			Assert.notNull(complaint);

			final Complaint saved = this.complaintService.save(complaint);
			final Collection<Complaint> complaints = new ArrayList<>(this.complaintService.findAll());
			Assert.isTrue(complaints.contains(saved));

			System.out.println("�Exito!");

		} catch (final Exception e) {
			System.out.println("�Fallo, " + e.getMessage() + "!");
		}
	}
	/*
	 * @Test public void testDelete() {
	 * System.out.println("========== testDelete() ==========");
	 *
	 * this.authenticate("handyWorker2"); final int handyWorkerId =
	 * this.getEntityId("handyWorker2");
	 *
	 * try {
	 *
	 * final Curriculum curriculum =
	 * this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);
	 * Assert.notNull(curriculum);
	 *
	 * this.curriculumService.delete(curriculum);
	 *
	 * final Collection<Curriculum> curriculums = new
	 * ArrayList<>(this.curriculumService.findAll());
	 * Assert.isTrue(!curriculums.contains(curriculum));
	 *
	 * System.out.println("�Exito!");
	 *
	 * } catch (final Exception e) { System.out.println("�Fallo," +
	 * e.getMessage() + "!"); }
	 *
	 * this.unauthenticate();
	 *
	 * }
	 */
}

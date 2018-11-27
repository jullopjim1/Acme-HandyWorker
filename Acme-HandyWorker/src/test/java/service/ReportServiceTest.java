
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

import services.ReportService;
import utilities.AbstractTest;
import domain.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ReportServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private ReportService	reportService;


	//Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		final int complaintId = this.getEntityId("complaint4");
		final int refereeId = this.getEntityId("referee1");
		try {
			final Report report = this.reportService.create(complaintId, refereeId);
			report.setDescription("JIJIJIJI");
			report.setAttachments("http://www.attachments1.com");
			Assert.notNull(report);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int reportId = this.getEntityId("report1");

		try {
			final Report report = this.reportService.findOne(reportId);
			Assert.notNull(report);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		final int reportId = this.getEntityId("report1");

		try {
			final Report report = this.reportService.findOne(reportId);
			final Collection<Report> reports = new ArrayList<>(this.reportService.findAll());
			Assert.notNull(report);
			Assert.isTrue(reports.contains(report));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		final int complaintId = this.getEntityId("complaint4");
		final int refereeId = this.getEntityId("referee1");
		try {
			final Report report = this.reportService.create(complaintId, refereeId);
			report.setDescription("JIJIJIJI");
			report.setAttachments("http://www.attachments1.com");
			Assert.notNull(report);

			final Report saved = this.reportService.save(report);
			final Collection<Report> reports = new ArrayList<>(this.reportService.findAll());
			Assert.isTrue(reports.contains(saved));

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

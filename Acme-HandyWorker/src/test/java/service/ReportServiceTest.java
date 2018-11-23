
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ComplaintService;
import services.RefereeService;
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
	private ReportService		reportService;

	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	//Test-------------------------------------------------------------

	@Test
	public void testReport() {
		System.out.println("------Test Report------");
		final Report report, saved;
		final Collection<Report> reports;

		report = this.reportService.create();
		System.out.println("Id de complaint: " + (this.getEntityId("complaint2")));
		this.complaintService.findOne(this.getEntityId("complaint2"));
		this.refereeService.findOne(this.getEntityId("referee2"));

		report.setDescription("JIJIJIJI");
		report.setAttachments("http://www.attachments1.com");

		saved = this.reportService.save(report);
		Assert.notNull(saved);

		reports = this.reportService.findAll();

		Assert.isTrue(reports.contains(saved));
	}
}


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
import utilities.AbstractTest;
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private ComplaintService	complaintService;


	//Test-------------------------------------------------------------

	@Test
	public void testComplaint() {
		System.out.println("------Test Complaint------");
		final Complaint complaint, saved;
		final Collection<Complaint> complaints;
		final int customerId = this.getEntityId("customer1");
		complaint = this.complaintService.create(customerId);
		try {
			complaint.setAttachments("http://www.attachments1.com");
			complaint.setDescription("Te como la verga");

			saved = this.complaintService.save(complaint);
			Assert.notNull(saved);
			complaints = this.complaintService.findAll();

			Assert.isTrue(complaints.contains(saved));
			System.out.println("�xito");
		} catch (final Exception e) {
			System.out.println("�Fallo," + e.getMessage() + "!");
		}
	}
}

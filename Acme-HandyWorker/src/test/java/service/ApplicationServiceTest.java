
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.FixUpTaskService;
import services.HandyWorkerService;
import utilities.AbstractTest;
import domain.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private ApplicationService		applicationService;
	
	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;
	//Test-------------------------------------------------------------

	@Test
	public void test() {
		final Application application = applicationService.create();
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		final int handyWorkerId = this.getEntityId("handyWorker1");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		Application saved = applicationService.save(application);
		Assert.isTrue(applicationService.findAll().contains(saved));
		Assert.isTrue(applicationService.findOne(saved.getId()) == saved);
		saved.setPrice(28);
		Application saved2 = applicationService.save(saved);
		Assert.isTrue(applicationService.findOne(saved2.getId()).getPrice()==28);
		applicationService.delete(saved);
		Assert.isTrue(!applicationService.findAll().contains(saved));
	}
}

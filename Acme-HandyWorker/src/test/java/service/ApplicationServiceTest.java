
package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.CreditCardService;
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
	
	@Autowired
	private CreditCardService		creditCardService;
	
	//Test-------------------------------------------------------------

	@Test
	public void test() {
		//LOGIN COMO HANDYWORKER
		authenticate("handyworker1");
		
		//CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create();
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		final int handyWorkerId = this.getEntityId("handyWorker1");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));
		
		//FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));
		
		//GUARDO APPLICATION
		Application saved = applicationService.save(application);
		
		//CHECK FINDALL
		Assert.isTrue(applicationService.findAll().contains(saved));
		
		//CHECK FINDONE
		Assert.isTrue(applicationService.findOne(saved.getId()) == saved);
		
		//EDITO APPLICATION (TAMBIEN CHEKEO RESTRICCION STATUS Y CREDITCARD)
		saved.setPrice(28);
		saved.setStatus("ACCEPTED");
		
		//LOGIN COMO CUSTOMER
		unauthenticate();
		authenticate("customer1");
		
		//GUARDO APPLICATION EDITADA
		Application saved2 = applicationService.save(saved);
		Assert.isTrue(applicationService.findOne(saved2.getId()).getPrice()==28);
		
		//LOGIN COMO ADMIN
		unauthenticate();
		authenticate("admin");
		
		//BORRO APPLICATION
		applicationService.delete(saved);
		Assert.isTrue(!applicationService.findAll().contains(saved));
		unauthenticate();
	}
}

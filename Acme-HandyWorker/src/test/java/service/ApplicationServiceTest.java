package service;

import java.util.ArrayList;

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
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private CreditCardService creditCardService;

	// Test-------------------------------------------------------------

	@Test
	public void testAll() {
		// LOGIN COMO HANDYWORKER
		authenticate("handyworker1");

		int handyWorkerId = getEntityId("handyWorker1");
		ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(
				fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));

		FixUpTask fixUpTask = fixUpTasks.get(0);
		// CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create(fixUpTask.getId());
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));

		// FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));

		// GUARDO APPLICATION
		Application saved = applicationService.save(application);

		// CHECK FINDALL
		Assert.isTrue(applicationService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(applicationService.findOne(saved.getId()) == saved);

		// EDITO APPLICATION (TAMBIEN CHEKEO RESTRICCION STATUS Y CREDITCARD)
		saved.setPrice(28);
		saved.setStatus("ACCEPTED");

		// LOGIN COMO CUSTOMER
		unauthenticate();
		authenticate("customer1");

		// GUARDO APPLICATION EDITADA
		@SuppressWarnings("unused")
		Application saved2 = applicationService.save(saved);

		// LOGIN COMO ADMIN
		unauthenticate();
		authenticate("admin");
	}

	@Test
	public void testCreate() {
		// LOGIN COMO HANDYWORKER
		authenticate("handyworker1");

		int handyWorkerId = getEntityId("handyWorker1");
		ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(
				fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));

		FixUpTask fixUpTask = fixUpTasks.get(0);

		// CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create(fixUpTask.getId());
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));

		// FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));
	}

	@Test
	public void testSave() {
		// LOGIN COMO HANDYWORKER
		authenticate("handyworker1");

		int handyWorkerId = getEntityId("handyWorker1");
		ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(
				fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));

		FixUpTask fixUpTask = fixUpTasks.get(0);
		// CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create(fixUpTask.getId());
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));

		// FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));

		// GUARDO APPLICATION
		applicationService.save(application);
	}

	@Test
	public void testFind() {
		// LOGIN COMO HANDYWORKER
		authenticate("handyworker1");
		int handyWorkerId = getEntityId("handyWorker1");
		ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(
				fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));

		FixUpTask fixUpTask = fixUpTasks.get(0);

		// CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create(fixUpTask.getId());
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));

		// FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));

		// GUARDO APPLICATION
		Application saved = applicationService.save(application);

		// CHECK FINDALL
		Assert.isTrue(applicationService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(applicationService.findOne(saved.getId()) == saved);
	}

	@Test
	public void testEdit() {
		// LOGIN COMO HANDYWORKER
		authenticate("handyworker1");
		int handyWorkerId = getEntityId("handyWorker1");
		ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(
				fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));

		FixUpTask fixUpTask = fixUpTasks.get(0);
		// CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create(fixUpTask.getId());
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));

		// FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));

		// GUARDO APPLICATION
		Application saved = applicationService.save(application);

		// CHECK FINDALL
		Assert.isTrue(applicationService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(applicationService.findOne(saved.getId()) == saved);

		// EDITO APPLICATION (TAMBIEN CHEKEO RESTRICCION STATUS Y CREDITCARD)
		saved.setPrice(28);
		saved.setStatus("ACCEPTED");

		// LOGIN COMO CUSTOMER
		unauthenticate();
		authenticate("customer1");

		// GUARDO APPLICATION EDITADA
		@SuppressWarnings("unused")
		Application saved2 = applicationService.save(saved);
	}

	@Test
	public void testDelete() {
		// LOGIN COMO HANDYWORKER
		authenticate("handyworker1");

		int handyWorkerId = getEntityId("handyWorker1");
		ArrayList<FixUpTask> fixUpTasks = new ArrayList<>(
				fixUpTaskService.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId));

		FixUpTask fixUpTask = fixUpTasks.get(0);

		// CREO APPLICATION Y SETEO ATRIBUTOS
		final Application application = applicationService.create(fixUpTask.getId());
		application.setStatus("PENDING");
		application.setPrice(23);
		application.setComments("comments");
		application.setHandyWorker(handyWorkerService.findOne(handyWorkerId));

		// FIXUPTASK DE CUSTOMER1
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		application.setFixUpTask(fixUpTaskService.findOne(fixUpTaskId));
		final int creditCardId = this.getEntityId("creditcard1");
		application.setCreditCard(creditCardService.findOne(creditCardId));

		// GUARDO APPLICATION
		Application saved = applicationService.save(application);

		// CHECK FINDALL
		Assert.isTrue(applicationService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(applicationService.findOne(saved.getId()) == saved);

		// EDITO APPLICATION (TAMBIEN CHEKEO RESTRICCION STATUS Y CREDITCARD)
		saved.setPrice(28);
		saved.setStatus("ACCEPTED");

		// LOGIN COMO CUSTOMER
		unauthenticate();
		authenticate("customer1");

		// GUARDO APPLICATION EDITADA
		@SuppressWarnings("unused")
		Application saved2 = applicationService.save(saved);
	}
}
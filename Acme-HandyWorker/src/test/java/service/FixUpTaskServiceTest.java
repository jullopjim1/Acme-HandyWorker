package service;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ApplicationService;
import services.CategoryService;
import services.CustomerService;
import services.FixUpTaskService;
import services.PhaseService;
import services.WarrantyService;
import utilities.AbstractTest;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private FixUpTaskService fixUpTaskService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private WarrantyService warrantyService;
	
	@Autowired
	private PhaseService phaseService;
	
	@Autowired
	private ApplicationService applicationService;

	// Test-------------------------------------------------------------

	@Test
	public void test() {
		final FixUpTask fixUpTask = fixUpTaskService.create();
		fixUpTask.setAdress("adress");
		fixUpTask.setDescription("description");
		fixUpTask.setMaxPrice(25);
		fixUpTask.setDeadline(new Date());
		final int categoryId = this.getEntityId("category1");
		final int customerId = this.getEntityId("customer1");
		final int warrantyId = this.getEntityId("warranty1");
		fixUpTask.setCategory(categoryService.findOne(categoryId));
		fixUpTask.setCustomer(customerService.findOne(customerId));
		fixUpTask.setWarranty(warrantyService.findOne(warrantyId));
		FixUpTask saved = fixUpTaskService.save(fixUpTask);
		Assert.isTrue(fixUpTaskService.findAll().contains(saved));
		Assert.isTrue(fixUpTaskService.findOne(saved.getId()) == saved);
		saved.setAdress("adressEdited");
		FixUpTask saved2 = fixUpTaskService.save(saved);
		Assert.isTrue(fixUpTaskService.findOne(saved2.getId()).getAdress() == "adressEdited");
		fixUpTaskService.delete(saved);
		Assert.isTrue(!fixUpTaskService.findAll().contains(saved));
		final int phaseId = this.getEntityId("phase1");
		final int fixUpTaskId = this.getEntityId("fixuptask3");
		fixUpTaskService.delete(fixUpTaskService.findOne(fixUpTaskId));
		Assert.isNull(phaseService.findOne(phaseId));
		final int applicationId = this.getEntityId("application3");
		Assert.isNull(applicationService.findOne(applicationId));
	}
}

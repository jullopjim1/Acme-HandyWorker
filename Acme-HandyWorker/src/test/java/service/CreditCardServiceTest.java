package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CreditCardService;
import utilities.AbstractTest;
import domain.CreditCard;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private CreditCardService creditCardService;

	// Test-------------------------------------------------------------

	@Test
	public void test() {
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");
		final int applicationId = this.getEntityId("application3");
		creditCard.set
		
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
		
		Assert.isNull(applicationService.findOne(applicationId));
	}
}

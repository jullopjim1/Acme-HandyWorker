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
	public void testAll() {
		// LOGIN COMO CUSTOMER
		authenticate("customer1");

		// CREO Y SETEO VALORES DE CREDITCARD
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");

		// GUARDO CREDITCARD
		CreditCard saved = creditCardService.save(creditCard);

		// CHECK FINDALL
		Assert.isTrue(creditCardService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(creditCardService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CREDITCARD EDITADA
		saved.setCVVCode(333);
		CreditCard saved2 = creditCardService.save(saved);
		Assert.isTrue(creditCardService.findOne(saved2.getId()).getCVVCode() == 333);

		// BORRO CREDITCARD
		creditCardService.delete(saved);
		Assert.isTrue(!creditCardService.findAll().contains(saved));
		unauthenticate();
	}

	@Test
	public void testCreate() {
		// LOGIN COMO CUSTOMER
		authenticate("customer1");

		// CREO Y SETEO VALORES DE CREDITCARD
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");
	}

	@Test
	public void testSave() {
		// LOGIN COMO CUSTOMER
		authenticate("customer1");

		// CREO Y SETEO VALORES DE CREDITCARD
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");

		// GUARDO CREDITCARD
		creditCardService.save(creditCard);
	}

	@Test
	public void testFind() {
		// LOGIN COMO CUSTOMER
		authenticate("customer1");

		// CREO Y SETEO VALORES DE CREDITCARD
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");

		// GUARDO CREDITCARD
		CreditCard saved = creditCardService.save(creditCard);

		// CHECK FINDALL
		Assert.isTrue(creditCardService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(creditCardService.findOne(saved.getId()) == saved);
	}

	@Test
	public void testEdit() {
		// LOGIN COMO CUSTOMER
		authenticate("customer1");

		// CREO Y SETEO VALORES DE CREDITCARD
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");

		// GUARDO CREDITCARD
		CreditCard saved = creditCardService.save(creditCard);

		// CHECK FINDALL
		Assert.isTrue(creditCardService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(creditCardService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CREDITCARD EDITADA
		saved.setCVVCode(333);
		CreditCard saved2 = creditCardService.save(saved);
		Assert.isTrue(creditCardService.findOne(saved2.getId()).getCVVCode() == 333);
	}

	@Test
	public void testDelete() {
		// LOGIN COMO CUSTOMER
		authenticate("customer1");

		// CREO Y SETEO VALORES DE CREDITCARD
		final CreditCard creditCard = creditCardService.create();
		creditCard.setNumber("4485807618846392");
		creditCard.setBrandName("VISA");
		creditCard.setCVVCode(222);
		creditCard.setExpirationMonth(12);
		creditCard.setExpirationYear(2019);
		creditCard.setHolderName("ADAM CIENFUEGOS IZQUIERDO");

		// GUARDO CREDITCARD
		CreditCard saved = creditCardService.save(creditCard);

		// CHECK FINDALL
		Assert.isTrue(creditCardService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(creditCardService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CREDITCARD EDITADA
		saved.setCVVCode(333);
		CreditCard saved2 = creditCardService.save(saved);
		Assert.isTrue(creditCardService.findOne(saved2.getId()).getCVVCode() == 333);

		// BORRO CREDITCARD
		creditCardService.delete(saved);
		Assert.isTrue(!creditCardService.findAll().contains(saved));
		unauthenticate();
	}
}

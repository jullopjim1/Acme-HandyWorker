package service;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import services.CustomerService;
import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Service----------------------------------------------------------

	@Autowired
	private CustomerService customerService;



	// Test-------------------------------------------------------------

	@Test
	public void test() {
		//NO LOGIN
		//CREO Y SETEO VALORES DE CUSTOMER
		final Customer customer = customerService.create();
		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername("customerT1");
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount.setPassword(encoder.encodePassword("customerT1",
				null));
		final Authority a = new Authority();
		a.setAuthority(Authority.CUSTOMER);
		userAccount.addAuthority(a);
		
		customer.setName("Augusto");
		customer.setMiddleName("Pepinero");
		customer.setSurname("Angostino");
		customer.setPhoto("http://www.photo122.com");
		customer.setEmail("customer@gmail.com");
		customer.setPhone("657824410");
		customer.setAddress("Calle chicoss");
		customer.setIsSuspicious(false);
		customer.setIsBanned(false);
		customer.setUserAccount(userAccount);
		
		//GUARDO CUSTOMER
		Customer saved = customerService.save(customer);
		
		//CHECK FINDALL
		Assert.isTrue(customerService.findAll().contains(saved));
		
		//CHECK FINDONE
		Assert.isTrue(customerService.findOne(saved.getId()) == saved);
		
		//LOGIN COMO CUSTOMER CREADO
		authenticate("customerT1");
		
		//EDITO VALOR Y GUARDO CUSTOMER EDITADO
		saved.setAddress("adressEdited");
		Customer saved2 = customerService.save(saved);
		Assert.isTrue(customerService.findOne(saved2.getId()).getAddress() == "adressEdited");
		
		//LOGIN COMO ADMIN
		unauthenticate();
		authenticate("admin");
		
		//BORRO CUSTOMER
		customerService.delete(saved2);
		Assert.isTrue(!customerService.findAll().contains(saved));
		unauthenticate();
	}
}

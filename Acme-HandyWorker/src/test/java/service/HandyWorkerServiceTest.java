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
import services.HandyWorkerService;
import utilities.AbstractTest;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private HandyWorkerService handyWorkerService;
	// Test-------------------------------------------------------------

	@Test
	public void test() {
		//NO LOGIN
		//CREO HANDYWORKER Y SETEO VALORES
		final HandyWorker handyWorker = handyWorkerService.create();
		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername("handyWorkerT1");
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount.setPassword(encoder.encodePassword("handyWorkerT1",
				null));
		final Authority a = new Authority();
		a.setAuthority(Authority.HANDY);
		userAccount.addAuthority(a);
		
		handyWorker.setName("Augusto");
		handyWorker.setMiddleName("Pepinero");
		handyWorker.setSurname("Angostino");
		handyWorker.setPhoto("http://www.photo122.com");
		handyWorker.setEmail("handy@gmail.com");
		handyWorker.setPhone("657824410");
		handyWorker.setAddress("Calle chico");
		handyWorker.setIsSuspicious(false);
		handyWorker.setIsBanned(false);
		handyWorker.setUserAccount(userAccount);
		
		//GUARDO HANDYWORKER
		HandyWorker saved = handyWorkerService.save(handyWorker);
		
		//CHECK FINDALL
		Assert.isTrue(handyWorkerService.findAll().contains(saved));
		
		//CHECK FINDONE
		Assert.isTrue(handyWorkerService.findOne(saved.getId()) == saved);
		
		//LOGIN COMO HANDYWORKER CREADO
		authenticate("handyWorkerT1");
		
		//EDITO VALOR Y GUARDO HANDYWORKER EDITADO
		saved.setAddress("adressEdited");
		HandyWorker saved2 = handyWorkerService.save(saved);
		Assert.isTrue(handyWorkerService.findOne(saved2.getId()).getAddress() == "adressEdited");
		
		//LOGIN COMO ADMIN
		unauthenticate();
		authenticate("admin");
		
		//BORRO HANDYWORKER
		handyWorkerService.delete(saved2);
		Assert.isTrue(!handyWorkerService.findAll().contains(saved));
		unauthenticate();
	}
}
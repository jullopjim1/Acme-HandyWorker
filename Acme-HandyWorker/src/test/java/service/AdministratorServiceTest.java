
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import services.AdministratorService;
import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private AdministratorService	administratorService;


	//Test-------------------------------------------------------------

	@Test
	public void testSaveAdministrator() {
		System.out.println("------Test Administrador------");
		final Administrator administrator, saved;
		final Collection<Administrator> administrators;

		administrator = this.administratorService.create();

		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername("administratorz1");
		userAccount.setPassword("administratorz2");
		final Authority a = new Authority();
		a.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(a);

		administrator.setName("Augusto");
		administrator.setMiddleName("Pepinero");
		administrator.setSurname("Angostino");
		administrator.setPhoto("FotoPolla");
		administrator.setEmail("administrador@gmail.com");
		administrator.setPhone("657824410");
		administrator.setAddress("Calle chico pene");
		administrator.setUserAccount(userAccount);

		saved = this.administratorService.save(administrator);

		administrators = this.administratorService.findAll();
		Assert.isTrue(administrators.contains(saved));

	}
}

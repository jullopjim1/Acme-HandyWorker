
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import services.RefereeService;
import utilities.AbstractTest;
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private RefereeService	refereeService;


	//Test-------------------------------------------------------------

	@Test
	public void testReferee() {
		System.out.println("------Test Referee------");
		final Referee referee, saved;
		final Collection<Referee> referees;
		try {

			referee = this.refereeService.create();
			final UserAccount userAccount = referee.getUserAccount();
			userAccount.setUsername("refereez1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("referee1", null));
			referee.setName("Augusto");
			referee.setMiddleName("Pepinero");
			referee.setSurname("Angostino");
			referee.setPhoto("http://www.photo122.com");
			referee.setEmail("administrador@gmail.com");
			referee.setPhone("657824410");
			referee.setAddress("Calle chico pene");
			referee.setUserAccount(userAccount);

			saved = this.refereeService.save(referee);
			Assert.notNull(saved);
			referees = this.refereeService.findAll();
			Assert.isTrue(referees.contains(saved));
			System.out.println("Éxito");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}
}

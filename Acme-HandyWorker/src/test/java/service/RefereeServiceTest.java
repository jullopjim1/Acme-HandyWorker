
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

		final UserAccount userAccount = new UserAccount();
		userAccount.setUsername("refereez1");
		userAccount.setPassword("refereez2");
		final Authority a = new Authority();
		a.setAuthority(Authority.REFEREE);
		userAccount.addAuthority(a);

		referee = this.refereeService.create();

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
	}
}

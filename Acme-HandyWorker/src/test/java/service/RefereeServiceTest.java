
package service;

import java.util.ArrayList;
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
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		try {
			final Referee referee = this.refereeService.create();
			final UserAccount userAccount = referee.getUserAccount();
			userAccount.setUsername("refereez1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("referee1", null));
			referee.setName("Augusto");
			referee.setMiddleName("Pepinero");
			referee.setSurname("Angostino");
			referee.setPhoto("http://www.photo122.com");
			referee.setEmail("referee@gmail.com");
			referee.setPhone("657824410");
			referee.setAddress("Calle chico pene");
			referee.setUserAccount(userAccount);

			Assert.notNull(userAccount);
			Assert.notNull(referee);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		this.authenticate("referee1");
		final int refereeId = this.getEntityId("referee1");

		try {
			final Referee referee = this.refereeService.findOne(refereeId);
			Assert.notNull(referee);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		this.authenticate("referee1");
		final int refereeId = this.getEntityId("referee1");

		try {
			final Collection<Referee> referees = new ArrayList<>(this.refereeService.findAll());
			final Referee referee = this.refereeService.findOne(refereeId);
			Assert.notNull(referee);

			Assert.isTrue(referees.contains(referee));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		try {
			final Referee referee = this.refereeService.create();
			final UserAccount userAccount = referee.getUserAccount();
			userAccount.setUsername("refereez1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("referee1", null));
			referee.setName("Augusto");
			referee.setMiddleName("Pepinero");
			referee.setSurname("Angostino");
			referee.setPhoto("http://www.photo122.com");
			referee.setEmail("referee@gmail.com");
			referee.setPhone("657824410");
			referee.setAddress("Calle chico pene");
			referee.setUserAccount(userAccount);

			final Referee saved = this.refereeService.save(referee);
			final Collection<Referee> referees = new ArrayList<>(this.refereeService.findAll());
			Assert.isTrue(referees.contains(saved));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo, " + e.getMessage() + "!");
		}
	}
	/*
	 * @Test
	 * public void testDelete() {
	 * System.out.println("========== testDelete() ==========");
	 * 
	 * this.authenticate("handyWorker2");
	 * final int handyWorkerId = this.getEntityId("handyWorker2");
	 * 
	 * try {
	 * 
	 * final Curriculum curriculum = this.curriculumService.findCurriculumHandyWorkerById(handyWorkerId);
	 * Assert.notNull(curriculum);
	 * 
	 * this.curriculumService.delete(curriculum);
	 * 
	 * final Collection<Curriculum> curriculums = new ArrayList<>(this.curriculumService.findAll());
	 * Assert.isTrue(!curriculums.contains(curriculum));
	 * 
	 * System.out.println("¡Exito!");
	 * 
	 * } catch (final Exception e) {
	 * System.out.println("¡Fallo," + e.getMessage() + "!");
	 * }
	 * 
	 * this.unauthenticate();
	 * 
	 * }
	 */
}

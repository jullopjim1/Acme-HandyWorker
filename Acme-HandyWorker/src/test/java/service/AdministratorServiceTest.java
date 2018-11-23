
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

import domain.Administrator;
import security.UserAccount;
import services.AdministratorService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;


	//Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		try {
			final Administrator admin = this.administratorService.create();
			final UserAccount userAccount = admin.getUserAccount();
			userAccount.setUsername("administratorz1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("admin", null));
			admin.setName("Augusto");
			admin.setName("Augusto");
			admin.setMiddleName("Pepinero");
			admin.setSurname("Angostino");
			admin.setPhoto("http://www.photo122.com");
			admin.setEmail("administrador@gmail.com");
			admin.setPhone("657824410");
			admin.setAddress("Calle chico pene");
			admin.setUserAccount(userAccount);

			Assert.notNull(userAccount);
			Assert.notNull(admin);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		this.authenticate("Admin");
		final int administratorId = this.getEntityId("admin");

		try {
			final Administrator admin = this.administratorService.findOne(administratorId);
			Assert.notNull(admin);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		this.authenticate("Admin");
		final int administratorId = this.getEntityId("admin");

		try {
			final Collection<Administrator> administrators = new ArrayList<>(this.administratorService.findAll());
			final Administrator admin = this.administratorService.findOne(administratorId);
			Assert.notNull(admin);
			Assert.isTrue(administrators.contains(admin));

			for (final Administrator admin1 : administrators)
				System.out.println(admin1.getUserAccount().getUsername());

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		try {
			final Administrator admin = this.administratorService.create();

			final UserAccount userAccount = admin.getUserAccount();
			userAccount.setUsername("administratorz1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("admin", null));
			admin.setUserAccount(userAccount);

			admin.setName("Chicharito");
			admin.setMiddleName("Pepinero");
			admin.setSurname("Angostino");
			admin.setPhoto("http://www.photo1224.com");
			admin.setEmail("administrador2@gmail.com");
			admin.setPhone("657824540");
			admin.setAddress("Calle pene");

			final Administrator saved = this.administratorService.save(admin);
			final Collection<Administrator> admins = new ArrayList<>(this.administratorService.findAll());
			Assert.isTrue(admins.contains(saved));

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

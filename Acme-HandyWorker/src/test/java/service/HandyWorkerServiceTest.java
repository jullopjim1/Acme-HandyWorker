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
import services.ApplicationService;
import services.CurriculumService;
import services.FinderService;
import services.HandyWorkerService;
import utilities.AbstractTest;
import domain.Finder;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private HandyWorkerService handyWorkerService;
	private CurriculumService curriculumService;
	private FinderService finderService;
	private ApplicationService applicationService;

	// Test-------------------------------------------------------------

	// @Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		try {
			final HandyWorker handyWorker = this.handyWorkerService.create();
			final UserAccount userAccount = handyWorker.getUserAccount();
			userAccount.setUsername("handyWorkerT1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("handyWorkerT1",
					null));
			handyWorker.setName("Augusto");
			handyWorker.setName("Augusto");
			handyWorker.setMiddleName("Pepinero");
			handyWorker.setSurname("Angostino");
			handyWorker.setPhoto("http://www.photo122.com");
			handyWorker.setEmail("administrador@gmail.com");
			handyWorker.setPhone("657824410");
			handyWorker.setAddress("Calle chico pene");
			handyWorker.setUserAccount(userAccount);

			Assert.notNull(userAccount);
			Assert.notNull(handyWorker);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	// @Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");

		try {
			final HandyWorker handyWorker = this.handyWorkerService
					.findOne(handyWorkerId);
			Assert.notNull(handyWorker);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	// @Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");

		try {
			final Collection<HandyWorker> handyWorkers = new ArrayList<>(
					this.handyWorkerService.findAll());
			final HandyWorker handyWorker = this.handyWorkerService
					.findOne(handyWorkerId);
			Assert.notNull(handyWorker);
			Assert.isTrue(handyWorkers.contains(handyWorker));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	// @Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		try {
			final HandyWorker handyWorker = this.handyWorkerService.create();

			final UserAccount userAccount = handyWorker.getUserAccount();
			userAccount.setUsername("handyWorkerT1");
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			userAccount.setPassword(encoder.encodePassword("handyWorkerT1",
					null));
			handyWorker.setUserAccount(userAccount);

			handyWorker.setName("Chicharito");
			handyWorker.setMiddleName("Pepinero");
			handyWorker.setSurname("Angostino");
			handyWorker.setPhoto("http://www.photo1224.com");
			handyWorker.setEmail("administrador2@gmail.com");
			handyWorker.setPhone("657824540");
			handyWorker.setAddress("Calle pene");

			final HandyWorker saved = this.handyWorkerService.save(handyWorker);
			final Collection<HandyWorker> handyWorkers = new ArrayList<>(
					this.handyWorkerService.findAll());
			Assert.isTrue(handyWorkers.contains(saved));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo, " + e.getMessage() + "!");
		}
	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		this.authenticate("Admin");
		final int handyWorkerId = this.getEntityId("handyWorker2");
		HandyWorker handyWorker = handyWorkerService.findOne(handyWorkerId);
		System.out.println(handyWorker);
		try {

			// BORRAR CURRICULUM
			// final Curriculum curriculum = this.curriculumService
			// .findCurriculumHandyWorkerById(handyWorkerId);
			// System.out.println(curriculum);
			// if (curriculum != null) {
			// this.curriculumService.delete(curriculum);
			//
			// final Collection<Curriculum> curriculums = new ArrayList<>(
			// this.curriculumService.findAll());
			// Assert.isTrue(!curriculums.contains(curriculum));
			// }
			//
			 //BORRAR FINDER
			 Finder finder = handyWorker.getFinder();
			 if(finder != null) {
			 finderService.delete(finder);
			 }
			
			// final Collection<Finder> finders = new ArrayList<>(
			// this.finderService.findAll());
			// Assert.isTrue(!finders.contains(finder));
			//
			//
			// //BORRAR APPLICATIONS
			// final Collection<Application> applications =
			// handyWorker.getApplications();
			// for(Application a : applications) {
			// applicationService.delete(a);
			// }
			//
			// BORRAR HANDYWORKER
			// handyWorkerService.delete(handyWorker);
			// Collection<HandyWorker> handyWorkers =
			// handyWorkerService.findAll();
			// Assert.isTrue(!handyWorkers.contains(handyWorker));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();
	}
}

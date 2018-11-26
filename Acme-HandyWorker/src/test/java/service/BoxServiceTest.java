
package service;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.BoxService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Box;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private BoxService		boxService;

	@Autowired
	private ActorService	actorService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		this.authenticate("handyWorker1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		try {
			final Box box = this.boxService.create();

			Assert.notNull(box, "Box creada no puede ser nula");
			Assert.isTrue(actor.equals(box.getActor()), "El actor asignado a cada carpeta debe ser igual al que esta logueado en el sistema");

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		final int boxId = this.getEntityId("box1");

		try {
			final Box box = this.boxService.findOne(boxId);
			Assert.notNull(box);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		try {
			final Collection<Box> boxes = new ArrayList<>(this.boxService.findAll());
			Assert.notEmpty(boxes);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		this.authenticate("handyWorker1");

		try {

			final Box box = this.boxService.create();
			Assert.notNull(box);

			box.setName("new Box");

			final Box saved = this.boxService.save(box);

			final Collection<Box> boxes = new ArrayList<>(this.boxService.findAll());
			Assert.isTrue(boxes.contains(saved));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("handyWorker1");
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		try {
			// Se añade las system Box a actor
			//boxService.addSystemBox(actor);

			final Box box = this.boxService.findOne(this.getEntityId("box1"));

			Assert.notNull(box);

			//			final Box boxHija1 = this.boxService.create();
			//			boxHija1.setRootbox(box);
			//			boxHija1.setName("boxHija1");
			//			final Box boxHija2 = this.boxService.create();
			//			boxHija2.setRootbox(box);
			//			boxHija2.setName("boxHija2");
			//			final Box boxHija3 = this.boxService.create();
			//			boxHija3.setRootbox(box);
			//			boxHija3.setName("boxHija3");
			//
			//			final Box boxNieta1 = this.boxService.create();
			//			boxNieta1.setRootbox(boxHija1);
			//			boxNieta1.setName("boxNieta1");
			//			final Box boxNieta2 = this.boxService.create();
			//			boxNieta2.setRootbox(boxHija2);
			//			boxNieta2.setName("boxNieta2");
			//			final Box boxNieta3 = this.boxService.create();
			//			boxNieta3.setRootbox(boxHija2);
			//			boxNieta3.setName("boxNieta1");
			//
			//			final Collection<Box> subboxes = box.getSubboxes();
			//
			//			subboxes.add(boxHija1);
			//			subboxes.add(boxHija2);
			//			subboxes.add(boxHija3);
			//
			//			final Collection<Box> subboxes1 = boxHija1.getSubboxes();
			//			subboxes1.add(boxNieta1);
			//
			//			final Collection<Box> subboxes2 = boxHija2.getSubboxes();
			//			subboxes2.add(boxNieta2);
			//			subboxes2.add(boxNieta2);
			//
			//			box.setSubboxes(subboxes);
			//
			//			this.boxService.save(boxHija1);
			//			this.boxService.save(boxHija2);
			//			this.boxService.save(boxHija3);
			//			this.boxService.save(boxNieta1);
			//			this.boxService.save(boxNieta2);
			//			this.boxService.save(boxNieta3);
			//			this.boxService.save(box);

			this.boxService.delete(box);
			final Collection<Box> boxes = this.boxService.findAll();

			Assert.isTrue(!boxes.contains(box));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}
}

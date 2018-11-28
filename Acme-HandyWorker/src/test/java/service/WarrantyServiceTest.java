
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

import services.WarrantyService;
import utilities.AbstractTest;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private WarrantyService	warrantyService;


	//Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		try {
			final Warranty warranty = this.warrantyService.create();

			warranty.setTitle("Garantia de tu puta madre");
			warranty.setTerms("Esto no vale para una mierda jaja salu2");
			warranty.setLaws("Chupala perro");
			Assert.notNull(warranty);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		final int warrantyId = this.getEntityId("warranty1");

		try {
			final Warranty warranty = this.warrantyService.findOne(warrantyId);
			Assert.notNull(warranty);
			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}

	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		final int warrantyId = this.getEntityId("warranty1");

		try {
			final Warranty warranty = this.warrantyService.findOne(warrantyId);
			final Collection<Warranty> warrantys = new ArrayList<>(this.warrantyService.findAll());
			Assert.notNull(warranty);
			Assert.isTrue(warrantys.contains(warranty));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		try {
			final Warranty warranty = this.warrantyService.create();

			warranty.setTitle("Garantia de tu puta madre");
			warranty.setTerms("Esto no vale para una mierda jaja salu2");
			warranty.setLaws("Chupala perro");
			Assert.notNull(warranty);

			final Warranty saved = this.warrantyService.save(warranty);
			final Collection<Warranty> warrantys = new ArrayList<>(this.warrantyService.findAll());
			Assert.isTrue(warrantys.contains(saved));

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

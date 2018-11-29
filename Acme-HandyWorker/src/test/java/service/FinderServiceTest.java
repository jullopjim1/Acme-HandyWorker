
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

import services.FinderService;
import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private FinderService	finderService;


	//@Autowired
	//private FixUpTaskService	fixUpTaskService;

	//Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		this.authenticate("handyWorker1");
		try {
			final Finder finder = this.finderService.create();

			Assert.notNull(finder);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");
		this.authenticate("handyWorker1");
		final int finderId = this.getEntityId("finder1");

		try {
			final Finder finder = this.finderService.findOne(finderId);
			Assert.notNull(finder);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
		this.unauthenticate();
	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		try {
			final Collection<Finder> finders = new ArrayList<>(this.finderService.findAll());
			Assert.notEmpty(finders);

			System.out.println("¡Exito!");

		} catch (final Exception e) {

			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSaveFinder() {
		System.out.println("------Test save Finder------");

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");

		Finder finder = this.finderService.findFinderByHandyWorkerId(handyWorkerId);
		finder.setKeyword(null);
		finder.setPriceMin(null);
		finder.setPriceMax(null);

		Assert.notNull(finder.getFixUpTasks());

		finder = this.finderService.updateFinder(finder);

		Assert.notEmpty(finder.getFixUpTasks());

		System.out.println("¡Exito!");

		this.unauthenticate();

	}
}

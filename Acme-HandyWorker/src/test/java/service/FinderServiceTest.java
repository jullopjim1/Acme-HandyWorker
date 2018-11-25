
package service;

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
	public void testUpdateFinder() {
		System.out.println("------Test Update Finder------");

		this.authenticate("handyWorker1");
		final int handyWorkerId = this.getEntityId("handyWorker1");

		try {
			Finder finder = this.finderService.findFinderByHandyWorkerId(handyWorkerId);
			Assert.notNull(finder.getFixUpTasks());
			System.out.println(finder.getFixUpTasks());

			finder = this.finderService.updateFinder(finder);

			Assert.notEmpty(finder.getFixUpTasks());

			System.out.println("¡Exito!");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}
}

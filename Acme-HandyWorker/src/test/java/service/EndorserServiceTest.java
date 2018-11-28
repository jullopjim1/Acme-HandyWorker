
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

import services.EndorserService;
import utilities.AbstractTest;
import domain.Endorser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorserServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private EndorserService	endorserService;


	//Tests

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		final int endorserId = this.getEntityId("customer1");

		try {
			final Endorser endorser = this.endorserService.findOne(endorserId);
			Assert.notNull(endorser);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		try {
			final Collection<Endorser> endorsers = new ArrayList<>(this.endorserService.findAll());
			Assert.notEmpty(endorsers);

			System.out.println("¡Exito!");

		} catch (final Exception e) {

			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

}


package service;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.PhaseService;
import utilities.AbstractTest;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private PhaseService	phaseService;


	//Test-------------------------------------------------------------

	@SuppressWarnings("deprecation")
	@Test
	public void testPhase() {
		System.out.println("------Test Phase------");
		final Phase phase, saved;
		final Collection<Phase> phases;
		final int fixUpTaskId = this.getEntityId("fixuptask1");
		phase = this.phaseService.create(fixUpTaskId);
		try {
			phase.setTitle("Hola");
			phase.setDescription("hijos de putaaaaaaaaaaa este test es la polla");
			phase.setStartMoment(new Date("2016/01/02 12:12"));
			phase.setEndMoment(new Date("2018/01/02 12:12"));

			saved = this.phaseService.save(phase);
			Assert.notNull(saved);
			phases = this.phaseService.findAll();
			Assert.isTrue(phases.contains(saved));
			System.out.println("Éxito");
		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}
	}
}

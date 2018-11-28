
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

import services.ConfigurationService;
import services.EndorsementService;
import utilities.AbstractTest;
import domain.Configuration;
import domain.Endorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private ConfigurationService	configurationService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");
		this.authenticate("customer1");
		try {
			final Endorsement endorsement = this.endorsementService.create();

			Assert.notNull(endorsement);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		final int endorsementId = this.getEntityId("endorsement1");

		try {
			final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			Assert.notNull(endorsement);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		try {
			final Collection<Endorsement> endorsements = this.endorsementService.findAll();
			Assert.notEmpty(endorsements);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");
		this.authenticate("customer1");

		final int endorsementId = this.getEntityId("endorsement1");

		try {

			final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			Assert.notNull(endorsement);

			String comments = "";

			final Configuration configuration = this.configurationService.findAll().iterator().next();

			for (final String negativeWord : configuration.getNegativeWords())
				comments = comments.concat(negativeWord).concat(" ");
			comments = comments.concat(configuration.getPositiveWords().iterator().next());

			//System.err.println("\n------" + comments);

			endorsement.setComments(comments);

			final Endorsement saved = this.endorsementService.save(endorsement);

			final Collection<Endorsement> endorsements = new ArrayList<>(this.endorsementService.findAll());
			Assert.isTrue(endorsements.contains(saved));

			final double nSize = configuration.getNegativeWords().size();
			final double total = nSize + configuration.getPositiveWords().size();

			final double score = (1 - nSize) / total;
			//System.err.println(score);
			Assert.isTrue(saved.getScore() == score, "Error en la puntuación de la endorsement");

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}
	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");
		this.authenticate("customer1");

		final int endorsementId = this.getEntityId("endorsement1");

		try {

			final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
			Assert.notNull(endorsement);

			this.endorsementService.delete(endorsement);

			final Collection<Endorsement> endorsements = new ArrayList<>(this.endorsementService.findAll());
			Assert.isTrue(!endorsements.contains(endorsement));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

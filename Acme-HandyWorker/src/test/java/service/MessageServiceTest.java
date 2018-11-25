
package service;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.MessageService;
import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private MessageService	messageService;

	//Tests
	//	@Test
	//	public void testCreate() {
	//		System.out.println("========== testCreate() ==========");
	//
	//		try {
	//			final Message message = this.configurationService.create();
	//
	//			Assert.notNull(message);
	//
	//			System.out.println("¡Exito!");
	//
	//		} catch (final Exception e) {
	//			System.out.println("¡Fallo," + e.getMessage() + "!");
	//		}
	//
	//		this.unauthenticate();
	//
	//	}
	//
	//	@Test
	//	public void testFindOne() {
	//		System.out.println("========== testFindOne() ==========");
	//
	//		final int configurationId = this.getEntityId("Configuration1");
	//
	//		try {
	//			final Configuration configuration = this.configurationService.findOne(configurationId);
	//			Assert.notNull(configuration);
	//
	//			System.out.println("¡Exito!");
	//
	//		} catch (final Exception e) {
	//			System.out.println("¡Fallo," + e.getMessage() + "!");
	//		}
	//
	//	}
	//	@Test
	//	public void testFindAll() {
	//		System.out.println("========== testFindAll() ==========");
	//
	//		try {
	//			final Collection<Configuration> configurations = new ArrayList<>(this.configurationService.findAll());
	//			Assert.notEmpty(configurations);
	//
	//			System.out.println("¡Exito!");
	//
	//		} catch (final Exception e) {
	//			System.out.println("==========");
	//			System.out.println(e);
	//			System.out.println("==========");
	//			System.out.println("¡Fallo," + e.getMessage() + "!");
	//		}
	//
	//	}
	//
	//	@Test
	//	public void testSave() {
	//		System.out.println("========== testSave() ==========");
	//
	//		final int configurationId = this.getEntityId("Configuration1");
	//
	//		try {
	//
	//			final Configuration configuration = this.configurationService.findOne(configurationId);
	//			Assert.notNull(configuration);
	//
	//			configuration.setCountryCode(22);
	//
	//			final Configuration saved = this.configurationService.save(configuration);
	//
	//			final Collection<Configuration> configurations = new ArrayList<>(this.configurationService.findAll());
	//			Assert.isTrue(configurations.contains(saved));
	//
	//			Assert.isTrue(saved.getCountryCode() == 22);
	//
	//			System.out.println("¡Exito!");
	//
	//		} catch (final Exception e) {
	//			System.out.println("¡Fallo," + e.getMessage() + "!");
	//		}
	//
	//		this.unauthenticate();
	//
	//	}
	//
	//	@Test
	//	public void testDelete() {
	//		System.out.println("========== testDelete() ==========");
	//
	//		final int configurationId = this.getEntityId("Configuration1");
	//
	//		try {
	//
	//			final Configuration configuration = this.configurationService.findOne(configurationId);
	//			Assert.notNull(configuration);
	//
	//			this.configurationService.delete(configuration);
	//
	//			final Collection<Configuration> configurations = new ArrayList<>(this.configurationService.findAll());
	//			Assert.isTrue(!configurations.contains(configuration));
	//
	//			System.out.println("¡Exito!");
	//
	//		} catch (final Exception e) {
	//			System.out.println("¡Fallo," + e.getMessage() + "!");
	//		}
	//
	//		this.unauthenticate();
	//
	//	}

}

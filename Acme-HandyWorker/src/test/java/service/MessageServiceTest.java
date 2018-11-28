
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.ActorService;
import services.MessageService;
import utilities.AbstractTest;
import domain.Actor;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private MessageService	messageService;

	@Autowired
	private ActorService	actorService;


	//Tests
	@Test
	public void testCreate() {
		System.out.println("========== testCreate() ==========");

		try {
			final Message message = this.messageService.create();

			Assert.notNull(message);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testFindOne() {
		System.out.println("========== testFindOne() ==========");

		final int messageId = this.getEntityId("message1");

		try {
			final Message message = this.messageService.findOne(messageId);
			Assert.notNull(message);

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}
	@Test
	public void testFindAll() {
		System.out.println("========== testFindAll() ==========");

		try {
			final Collection<Message> messages = this.messageService.findAll();
			Assert.notEmpty(messages);

			System.out.println("¡Exito!");

		} catch (final Exception e) {

			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

	}

	@Test
	public void testSave() {
		System.out.println("========== testSave() ==========");

		this.authenticate("customer1");

		try {

			final Message message = this.messageService.create();
			Assert.notNull(message);

			final Actor customer2 = this.actorService.findOne(this.getEntityId("customer2"));
			message.setRecipient(customer2);

			final Message saved = this.messageService.save(message);

			final Collection<Message> messages = this.messageService.findAll();
			Assert.isTrue(messages.contains(saved));

			Assert.isTrue(saved.getRecipient().equals(customer2));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

	@Test
	public void testDelete() {
		System.out.println("========== testDelete() ==========");

		final int messageId = this.getEntityId("message1");

		try {

			final Message message = this.messageService.findOne(messageId);
			Assert.notNull(message);

			this.messageService.delete(message);

			final Message messageDelete = this.messageService.findOne(messageId);

			if (messageDelete == null) {
				final Collection<Message> messages = this.messageService.findAll();

				Assert.isTrue(!messages.contains(message));
			} else
				Assert.isTrue(message.getBox().equals("trash box"));

			System.out.println("¡Exito!");

		} catch (final Exception e) {
			System.out.println("¡Fallo," + e.getMessage() + "!");
		}

		this.unauthenticate();

	}

}

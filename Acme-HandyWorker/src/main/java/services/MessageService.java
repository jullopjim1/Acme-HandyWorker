
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Box;
import domain.Configuration;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	//Services---------------------------------------------------------------------------
	@Autowired
	private BoxService				boxService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ConfigurationService	configurationService;


	//Constructor------------------------------------------------------------------------

	public MessageService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Message create() {
		final Message message = new Message();
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount, "Debe estar logeado en el sistema para crear una carpeta");
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final String subject = "";
		final String body = "";
		final Date moment = new Date();
		final String priority = "LOW";
		final String tags = "";

		final Box box = this.boxService.create();
		final Actor sender = actor;
		//final Actor recipient = this.actorService.create();

		message.setSubject(subject);
		message.setBody(body);
		message.setMoment(moment);
		message.setPriority(priority);
		message.setTags(tags);
		message.setBox(box);
		message.setSender(sender);

		return message;

	}

	public Collection<Message> findAll() {
		final Collection<Message> messages = this.messageRepository.findAll();
		Assert.notNull(messages);
		return messages;
	}

	public Message findOne(final Integer messageId) {
		return this.messageRepository.findOne(messageId);
	}

	public Message save(final Message message) {

		final boolean isSpam = this.isSpam(message);

		final Actor sender = message.getSender();
		final Actor recipient = message.getRecipient();

		final Box outBoxSender = this.boxService.findBoxByActorIdAndName(sender.getId(), "out Box");
		Assert.notNull(outBoxSender, "NULL OUT BOX\ncada actor debe tener debe tener un out Box");

		final Message messageSend = message;
		final Message messageRecipient = message;

		messageSend.setBox(outBoxSender);

		Box spamBoxRecipient;
		Box inBoxRecipient;
		if (isSpam) {
			spamBoxRecipient = this.boxService.findBoxByActorIdAndName(recipient.getId(), "spam Box");
			Assert.notNull(spamBoxRecipient, "NULL SPAM BOX\nCada actor debe tener un spam box");
			messageRecipient.setBox(spamBoxRecipient);
			this.administratorService.isSuspicious(sender);

		} else {
			inBoxRecipient = this.boxService.findBoxByActorIdAndName(recipient.getId(), "in Box");
			Assert.notNull(inBoxRecipient, "NULL IN BOX\nCada actor debe tener un in box");
			messageRecipient.setBox(inBoxRecipient);
		}

		final Message saved = this.messageRepository.save(messageRecipient);
		this.messageRepository.save(messageSend);

		return saved;
	}
	public void delete(final Message entity) {
		final Box box = entity.getBox();
		final Actor actor = this.checkPrincipal(entity);

		if (box.getName().equals("trash box"))
			this.messageRepository.delete(entity);
		else {
			final Box trash = this.boxService.findBoxByActorIdAndName(actor.getId(), "trash box");
			Assert.notNull(trash, "Todo actor debe tener un trash box");
			entity.setBox(trash);
			this.messageRepository.save(entity);
		}

	}

	public void deleteByBox(final Box box) {
		if (this.messageRepository.findByBoxId(box.getId()) != null || !this.messageRepository.findByBoxId(box.getId()).isEmpty())
			this.messageRepository.delete(this.messageRepository.findByBoxId(box.getId()));

	}
	//Other Methods---------------------------------------------------------------------------

	public Collection<Message> findByBox(final Box box) {
		Assert.notNull(box, "findByBox - Box must not be null");

		final Collection<Message> result = this.messageRepository.findByBoxId(box.getId());

		return result;

	}
	public Message moveMessage(final Message message, final Box newBox) {
		this.checkPrincipal(message);
		message.setBox(newBox);
		final Message saved = this.messageRepository.save(message);
		return saved;

	}

	/*
	 * Requisito Funcional 12.4
	 */
	public void broadcastMessage(final Message message) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount, "Debe estar logeado en el sistema para crear una carpeta");

		final Authority authority = new Authority();
		authority.setAuthority("ADMIN");

		Assert.isTrue(userAccount.getAuthorities().contains(authority), "Solo los administradores pueden realizar mensajes de difusi�n");

		final Collection<Actor> allActor = this.actorService.findAll();

		final Collection<Message> messages = new ArrayList<>();

		for (final Actor recipient : allActor) {
			final Message message2 = message;
			message2.setRecipient(recipient);
			messages.add(message2);
		}

		this.messageRepository.save(messages);

	}
	private Actor checkPrincipal(final Message message) {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount, "Debe estar logeado para modificar o borrar un mensaje");
		final Actor actor = this.actorService.findByUserAccount(userAccount);
		Assert.isTrue(message.getSender().equals(actor) || message.getRecipient().equals(actor), "Un actor solo puede ver sus mensajes");
		return actor;
	}
	private boolean isSpam(final Message message) {
		boolean result = false;
		final Configuration configuration = this.configurationService.findOne();
		final Map<String, Collection<String>> spamWords = configuration.getSpamWords();

		final Collection<String> keySet = spamWords.keySet();

		for (final String key : keySet) {
			final Collection<String> spamList = spamWords.get(key);
			for (final String spamWord : spamList)
				if (message.getBody().contains(spamWord) || message.getSubject().contains(spamWord)) {
					result = true;
					break;
				}
			if (result = true)
				break;
		}

		return result;
	}
}

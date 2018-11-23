
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.MessageRepository;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private MessageRepository	messageRepository;


	//Services---------------------------------------------------------------------------

	//Constructor------------------------------------------------------------------------

	public MessageService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Message create() {
		final Message message = new Message();

		return message;

	}

	public List<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final Integer messageId) {
		return this.messageRepository.findOne(messageId);
	}
	public Message save(final Message message) {
		final Message saved = this.messageRepository.save(message);
		return saved;
	}

	public void delete(final Message entity) {
		this.messageRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

}

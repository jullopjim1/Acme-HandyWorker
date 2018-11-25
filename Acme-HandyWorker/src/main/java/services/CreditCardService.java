package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	//Repository-----------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;
	//Services-------------------------------------------------

	//Constructor----------------------------------------------
	public CreditCardService() {
		super();
	}
	//Simple CRUD----------------------------------------------

	public CreditCard create() {
		final CreditCard creditCard = new CreditCard();
		return creditCard;
	}

	public List<CreditCard> findAll() {
		return this.creditCardRepository.findAll();
	}

	public CreditCard findOne(final Integer creditCardId) {
		return this.creditCardRepository.findOne(creditCardId);
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		final CreditCard saved = this.creditCardRepository.save(creditCard);
		return saved;
	}

	public void delete(final CreditCard entity) {
		this.creditCardRepository.delete(entity);

	}
	//Other Methods--------------------------------------------

}
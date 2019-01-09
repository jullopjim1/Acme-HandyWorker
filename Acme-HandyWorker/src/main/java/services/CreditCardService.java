
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import security.LoginService;
import domain.Actor;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	// Repository-----------------------------------------------
	@Autowired
	private CreditCardRepository	creditCardRepository;

	// Services-------------------------------------------------
	@Autowired
	private ActorService			actorService;


	// Constructor----------------------------------------------
	public CreditCardService() {
		super();
	}

	// Simple CRUD----------------------------------------------

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
		Assert.isTrue(this.isGood(creditCard), "errorCredit");

		Assert.notNull(creditCard, "CREDITCARD A CREAR/EDITAR NO PUEDE SER NULL");

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (creditCard.getId() == 0)
			Assert.isTrue(actorActual.getUserAccount().getAuthorities().toString().contains("CUSTOMER") || actorActual.getUserAccount().getAuthorities().toString().contains("SPONSOR"), "CREAR CREDITCARD -> SPONSOR O CUSTOMER");

		//TODO FALTA RESTRICCION DE EDITAR (EN CONTROLADOR).

		// GUARDO CREDITCARD
		final CreditCard saved = this.creditCardRepository.save(creditCard);
		return saved;
	}

	public void delete(final CreditCard creditCard) {
		//TODO SOLO PUEDE BORRAR LA CREDITCARD SU PROPIETARIO Y EL ADMIN
		this.creditCardRepository.delete(creditCard);

	}
	// Other Methods--------------------------------------------
	public boolean isGood(final CreditCard creditCard) {
		boolean res = false;
		if (creditCard.getExpirationYear() >= LocalDate.now().getYear())
			if (creditCard.getExpirationMonth() >= LocalDate.now().getMonthOfYear())
				res = true;
		return res;
	}
}

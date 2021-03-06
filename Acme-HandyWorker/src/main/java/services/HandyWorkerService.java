
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.HandyWorker;
import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class HandyWorkerService {

	// Repository-----------------------------------------------

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Services-------------------------------------------------
	@Autowired
	private ActorService			actorService;


	// Constructor----------------------------------------------

	public HandyWorkerService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public HandyWorker create() {
		final HandyWorker handyWorker = new HandyWorker();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority("HANDY");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		userAccount.setEnabled(true);
		handyWorker.setUserAccount(userAccount);
		handyWorker.setIsBanned(false);
		handyWorker.setIsSuspicious(false);

		return handyWorker;
	}

	public List<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(final Integer handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker, "HANDYWORKER A GUARDAR NO PUEDE SER NULL");

		// SI NO ES USUARIO NUEVO
		if (handyWorker.getId() != 0) {
			// COJO ACTOR ACTUAL
			final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
			Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

			// COMPRUEBO RESTRICCIONES DE USUARIOS
			if (!(actorActual.getId() == handyWorker.getId()))
				Assert.notNull(null, "UN HANDYWORKER SOLO PUEDE EDITAR SUS PROPIOS DATOS PERSONALES");
		}

		// MAKE POR DEFECTO
		if (handyWorker.getMake().isEmpty())
			handyWorker.setMake(handyWorker.getSurname() + " " + handyWorker.getMiddleName() + " " + handyWorker.getName());

		// GUARDO HANDYWORKER
		final HandyWorker saved = this.handyWorkerRepository.save(handyWorker);
		return saved;
	}

	public void delete(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker, "HANDYWORKER A BORRAR NO PUEDE SER NULL");

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString().contains("ADMIN"))
			Assert.notNull(null, "SOLO ADMIN PUEDE BORRAR HANDYWORKER");

		// BORRO HANDYWORKER
		this.handyWorkerRepository.delete(handyWorker);
	}

	// Other Methods--------------------------------------------

	public HandyWorker findHandyWorkerByPhaseId(final int phaseId) {
		return this.handyWorkerRepository.findHandyWorkerByPhaseId(phaseId);
	}

	public HandyWorker findHandyWorkerByUserAccount(final int userAccountId) {
		return this.handyWorkerRepository.findHandyWorkerByUserAccount(userAccountId);
	}

	public HandyWorker isSuspicious(final HandyWorker handyWorker) {
		final HandyWorker saved = this.handyWorkerRepository.save(handyWorker);

		return saved;
	}

	public boolean isPrincipalHandy() {
		boolean res = false;
		final UserAccount u = LoginService.getPrincipal();
		final Authority a = new Authority();
		a.setAuthority("HANDY");
		if (u.getAuthorities().contains(a))
			res = true;
		return res;
	}

	public HandyWorker findHandyByUsername(final String username) {
		return this.handyWorkerRepository.findHandyByUsername(username);
	}
}

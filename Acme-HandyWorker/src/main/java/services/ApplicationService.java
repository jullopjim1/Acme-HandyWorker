package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import domain.Actor;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	// Repository-----------------------------------------------
	@Autowired
	private ApplicationRepository applicationRepository;

	// Services-------------------------------------------------
	@Autowired
	private ActorService actorService;

	// Constructor----------------------------------------------
	public ApplicationService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Application create() {
		final Application application = new Application();
		application.setMoment(new Date(System.currentTimeMillis() - 1000));
		return application;
	}

	public List<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application application) {
		Assert.notNull(application,
				"APPLICATION A CREAR/EDITAR NO PUEDE SER NULL");

		// COJO ACTOR ACTUAL
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (actorActual.getUserAccount().getAuthorities().toString()
				.contains("CUSTOMER")) {
			boolean restriccion = (application.getId() != 0)
					&& (application.getFixUpTask().getCustomer().getId() == (actorActual
							.getId()));
			boolean restriccion2 = (application.getFixUpTask().getCustomer()
					.getId() == actorActual.getId());
			Assert.isTrue(restriccion, "CUSTOMER NO PUEDE CREAR APPLICATION");
			Assert.isTrue(restriccion2,
					"CUSTOMER SOLO PUEDE MODIFICAR APPLICATION DE SUS FIXUPTASKS");
		} else if (actorActual.getUserAccount().getAuthorities().toString()
				.contains("HANDY")) {
			boolean restriccion = (application.getId() == 0)
					&& (application.getHandyWorker().getId() == (actorActual
							.getId()));
			Assert.isTrue(restriccion, "HANDY NO PUEDE MODIFICAR APPLICATION");
		} else {
			Assert.notNull(null,
					"PARA CREAR APPLICATION -> HANDY, PARA MODIFICAR APPLICATION -> CUSTOMER");
		}

		// SOLO CAMBIAR FECHA SI ES NUEVO
		if (application.getId() == 0) {
			application.setMoment(new Date(System.currentTimeMillis() - 1000));
		}
		// GUARDO APPLICATION
		final Application saved = this.applicationRepository.save(application);
		return saved;
	}

	public void delete(final Application application) {
		Assert.notNull(application, "APPLICATION A BORRAR NO PUEDE SER NULL");

		// COJO ACTOR ACTUAL
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString()
				.contains("ADMIN")) {
			Assert.notNull(null, "SOLO ADMIN PUEDE BORRAR APPLICATION");
		}

		// BORRO APPLICATION
		this.applicationRepository.delete(application);

	}

	// Other Methods--------------------------------------------

	Collection<Application> findApplicationsByCreditCardId(int creditCardId) {
		Collection<Application> applications = applicationRepository
				.findApplicationsByCreditCardId(creditCardId);
		return applications;
	}
}
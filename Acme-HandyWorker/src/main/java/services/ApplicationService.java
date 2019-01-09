
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;
import repositories.ApplicationRepository;
import security.Authority;
import security.LoginService;

@Service
@Transactional
public class ApplicationService {

	// Repository-----------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;

	// Services-------------------------------------------------
	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;


	// Constructor----------------------------------------------
	public ApplicationService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Application create(final int fixUpTaskId) {
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(LoginService.getPrincipal().getId());
		final Application application = new Application();
		application.setMoment(new Date(System.currentTimeMillis() - 1000));
		application.setStatus("PENDING");
		application.setFixUpTask(fixUpTask);
		application.setHandyWorker(handyWorker);

		return application;
	}

	public List<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application application) {
		Assert.notNull(application, "APPLICATION A CREAR/EDITAR NO PUEDE SER NULL");
		final Authority handy = new Authority();
		handy.setAuthority("HANDY");
		final Authority cust = new Authority();
		cust.setAuthority("CUSTOMER");

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual.getId() == application.getHandyWorker().getId(), "NO HAY ACTOR DETECTADO");
		final Collection<Authority> authorities = actorActual.getUserAccount().getAuthorities();

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (authorities.contains(cust)) {
			final boolean restriccion = (application.getId() != 0) && (application.getFixUpTask().getCustomer().getId() == (actorActual.getId()));
			final boolean restriccion2 = (application.getFixUpTask().getCustomer().getId() == actorActual.getId());
			Assert.isTrue(restriccion, "CUSTOMER NO PUEDE CREAR APPLICATION");
			Assert.isTrue(restriccion2, "CUSTOMER SOLO PUEDE MODIFICAR APPLICATION DE SUS FIXUPTASKS");
		} else if (authorities.contains(handy)) {
			final boolean restriccion = (application.getHandyWorker().getId() != (actorActual.getId()));
			Assert.isTrue(restriccion, "HANDY NO PUEDE MODIFICAR APPLICATION");
		} else
			Assert.notNull(null, "PARA CREAR APPLICATION -> HANDY, PARA MODIFICAR APPLICATION -> CUSTOMER");

		// TARJETA DE CREDITO NECESARIA SI STATUS = ACCPETED
		if (application.getStatus() == "ACCEPTED")
			Assert.isTrue(application.getCreditCard() != null, "SI STATUS = ACCEPTED ES NECESARIA TARJETA DE CREDITO");

		if (application.getId() == 0)
			application.setMoment(new Date(System.currentTimeMillis() - 1000));
		// GUARDO APPLICATION
		final Application saved = this.applicationRepository.save(application);
		return saved;
	}

	public void delete(final Application application) {
		Assert.notNull(application, "APPLICATION A BORRAR NO PUEDE SER NULL");

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString().contains("ADMIN"))
			Assert.notNull(null, "SOLO ADMIN PUEDE BORRAR APPLICATION");

		// BORRO APPLICATION
		this.applicationRepository.delete(application);

	}

	// Other Methods--------------------------------------------

	Collection<Application> findApplicationsByCreditCardId(final int creditCardId) {
		final Collection<Application> applications = this.applicationRepository.findApplicationsByCreditCardId(creditCardId);
		return applications;
	}

	public Collection<Application> findApplicationsByFixUpTeaskId(final int fixUpTaskId) {
		return this.applicationRepository.findApplicationsByFixUpTaskId(fixUpTaskId);
	}

	public Collection<Application> findApplicationByHandyWorkerId(final int handyWorkerId) {
		return this.applicationRepository.findApplicationByHandyWorkerId(handyWorkerId);
	}

	public Double queryC2AVG() {
		return this.applicationRepository.queryC2AVG();
	}

	public Double queryC2MAX() {
		return this.applicationRepository.queryC2MAX();
	}

	public Double queryC2MIN() {
		return this.applicationRepository.queryC2MIN();
	}

	public Double queryC2STDDEV() {
		return this.applicationRepository.queryC2STDDEV();
	}

	public Object[] queryC4() {
		return this.applicationRepository.queryC4();
	}

	public Double queryC5() {
		return this.applicationRepository.queryC5();
	}

	public Double queryC6() {
		return this.applicationRepository.queryC6();
	}

	public Double queryC7() {
		return this.applicationRepository.queryC7();
	}

	public Double queryC8() {
		return this.applicationRepository.queryC8();
	}

	public ArrayList<HandyWorker> queryC10() {
		return this.applicationRepository.queryC10();
	}

	public ArrayList<Customer> queryC9() {
		return this.applicationRepository.queryC9();
	}

}

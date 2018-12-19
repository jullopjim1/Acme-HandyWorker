
package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Application;
import domain.FixUpTask;
import domain.Phase;
import domain.Ticker;
import repositories.FixUpTaskRepository;
import security.LoginService;

@Service
@Transactional
public class FixUpTaskService {

	// Repository-----------------------------------------------
	@Autowired
	private FixUpTaskRepository	fixUpTaskRepository;

	// Services-------------------------------------------------
	@Autowired
	private PhaseService		phaseService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private TickerService		tickerService;


	// Constructor----------------------------------------------
	public FixUpTaskService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public FixUpTask create() {
		final FixUpTask fixUpTask = new FixUpTask();

		//Ticker Unico
		final Ticker ticker = this.tickerService.isUniqueTicker();
		final Ticker saved = this.tickerService.save(ticker);

		fixUpTask.setMoment(new Date(System.currentTimeMillis() - 1000));
		fixUpTask.setTicker(saved);
		return fixUpTask;
	}

	public List<FixUpTask> findAll() {
		return this.fixUpTaskRepository.findAll();
	}

	public FixUpTask findOne(final Integer fixUpTaskId) {
		return this.fixUpTaskRepository.findOne(fixUpTaskId);
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// SI ES NUEVA FIXUPTASK
		if (fixUpTask.getId() == 0) {
			fixUpTask.setMoment(new Date(System.currentTimeMillis() - 1000));
			Assert.isTrue(actorActual.getUserAccount().getAuthorities().toString().contains("CUSTOMER"), "SOLO UN CUSTOMER PUEDE CREAR UNA FIXUPTASK");
			fixUpTask.setCustomer(this.customerService.findOne(actorActual.getId()));
		}

		// GUARDO FIXUPTASK
		final FixUpTask saved = this.fixUpTaskRepository.save(fixUpTask);
		return saved;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		this.tickerService.delete(fixUpTask.getTicker());

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!(actorActual.getId() == fixUpTask.getCustomer().getId()))
			if (!actorActual.getUserAccount().getAuthorities().toString().contains("ADMIN"))
				Assert.notNull(null, "SOLO EL CUSTOMER PUEDE BORRAR SU PROPIA FIXUPTASK O BIEN EL ADMIN");

		// BORRO SUS PHASES
		final Collection<Phase> phases = this.phaseService.findPhasesByFixUpTaskIdActive(fixUpTask.getId());
		for (final Phase p : phases)
			this.phaseService.deleteFromFixUpTask(p);

		// BORRO SUS APPLICATIONS
		final Collection<Application> applications = this.applicationService.findApplicationsByFixUpTeaskId(fixUpTask.getId());
		for (final Application a : applications)
			this.applicationService.delete(a);

		// BORRO LA FIXUPTASK
		this.fixUpTaskRepository.delete(fixUpTask);

	}

	// Other Methods--------------------------------------------

	public Collection<FixUpTask> findTasksByCategoryId(final int categoryId) {
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskRepository.findTasksByCategoryId(categoryId);
		return fixUpTasks;
	}

	public Collection<FixUpTask> findTasksActiveByApplicationAcceptedAndHandyWorkerId(final int handyWorkerId) {
		return this.fixUpTaskRepository.findTasksActiveByApplicationAcceptedAndHandyWorkerId(handyWorkerId);
	}

	public Collection<FixUpTask> findFixUpTaskByCustomerId(final int customerId) {
		return this.fixUpTaskRepository.findFixUpTaskByCustomerId(customerId);
	}

}

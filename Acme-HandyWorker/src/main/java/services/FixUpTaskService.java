package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import security.LoginService;
import domain.Actor;
import domain.Application;
import domain.Complaint;
import domain.FixUpTask;
import domain.Phase;

@Service
@Transactional
public class FixUpTaskService {

	// Repository-----------------------------------------------
	@Autowired
	private FixUpTaskRepository fixUpTaskRepository;

	// Services-------------------------------------------------
	@Autowired
	private PhaseService phaseService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private CustomerService customerService;

	// Constructor----------------------------------------------
	public FixUpTaskService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public FixUpTask create() {
		final FixUpTask fixUpTask = new FixUpTask();
		fixUpTask.setMoment(new Date(System.currentTimeMillis() - 1000));
		fixUpTask.setTicker(this.generateTicker());
		final Collection<Complaint> complaints = new ArrayList<Complaint>();
		final Collection<Application> applications = new ArrayList<Application>();
		fixUpTask.setComplaints(complaints);
		fixUpTask.setApplications(applications);
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
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// SI ES NUEVA FIXUPTASK
		if (fixUpTask.getId() == 0) {
			fixUpTask.setMoment(new Date(System.currentTimeMillis() - 1000));
			Assert.isTrue(actorActual.getUserAccount().getAuthorities()
					.toString().contains("CUSTOMER"),
					"SOLO UN CUSTOMER PUEDE CREAR UNA FIXUPTASK");
			fixUpTask.setCustomer(customerService.findOne(actorActual.getId()));
		}

		// GUARDO FIXUPTASK
		final FixUpTask saved = this.fixUpTaskRepository.save(fixUpTask);
		return saved;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		// COJO ACTOR ACTUAL
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!(actorActual.getId() == fixUpTask.getCustomer().getId())) {
			if (!actorActual.getUserAccount().getAuthorities().toString()
					.contains("ADMIN")) {
				Assert.notNull(null,
						"SOLO EL CUSTOMER PUEDE BORRAR SU PROPIA FIXUPTASK O BIEN EL ADMIN");
			}
		}

		// BORRO SUS PHASES
		Collection<Phase> phases = phaseService
				.findPhasesByFixUpTaskId(fixUpTask.getId());
		for (Phase p : phases) {
			phaseService.delete(p);
		}

		// BORRO SUS APPLICATIONS
		Collection<Application> applications = fixUpTask.getApplications();
		for (Application a : applications) {
			applicationService.delete(a);
		}

		// BORRO LA FIXUPTASK
		this.fixUpTaskRepository.delete(fixUpTask);

	}

	// Other Methods--------------------------------------------

	@SuppressWarnings("deprecation")
	public String generateTicker() {
		final Date date = new Date();
		final Integer s1 = date.getDate();
		String day = s1.toString();
		if (day.length() == 1)
			day = "0" + day;
		final Integer s2 = date.getMonth() + 1;
		String month = s2.toString();
		if (month.length() == 1)
			month = "0" + month;
		final Integer s3 = date.getYear();
		final String year = s3.toString().substring(1);

		return year + month + day + "-" + FixUpTaskService.generateStringAux();
	}

	private static String generateStringAux() {
		final int length = 6;
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 6; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public Collection<FixUpTask> findTasksByCategoryId(int categoryId) {
		Collection<FixUpTask> fixUpTasks = fixUpTaskRepository
				.findTasksByCategoryId(categoryId);
		return fixUpTasks;
	}

}
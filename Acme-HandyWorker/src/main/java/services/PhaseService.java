
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.LoginService;
import domain.FixUpTask;
import domain.HandyWorker;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Repository-----------------------------------------------

	@Autowired
	private PhaseRepository		phaseRepository;

	// Services-------------------------------------------------
	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private HandyWorkerService	handyWorkerService;


	// Constructor----------------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Phase create(final int fixUpTaskId) {
		final Phase phase = new Phase();
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);
		phase.setFixUpTask(fixUpTask);
		return phase;
	}

	public List<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	public Phase findOne(final Integer phaseId) {
		return this.phaseRepository.findOne(phaseId);
	}

	public Phase save(final Phase phase) {
		Assert.notNull(phase);
		if (phase.getId() != 0)
			this.checkPrincipal(phase);
		final Phase saved = this.phaseRepository.save(phase);
		return saved;
	}

	public void delete(final Phase phase) {
		this.checkPrincipal(phase);
		this.phaseRepository.delete(phase);
	}

	public void deleteFromFixUpTask(final Phase phase) {
		this.phaseRepository.delete(phase);
	}

	// Other Methods--------------------------------------------

	public Collection<Phase> findPhasesByFixUpTaskIdActive(final int fixUpTaskId) {
		return this.phaseRepository.findPhasesByFixUpTaskIdActive(fixUpTaskId);
	}

	public Boolean checkPrincipal(final Phase phase) {
		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByPhaseId(phase.getId());
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}
}

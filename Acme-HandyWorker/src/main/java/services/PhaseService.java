package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Repository-----------------------------------------------

	@Autowired
	private PhaseRepository phaseRepository;

	// Services-------------------------------------------------
	// @Autowired
	// private FixUpTaskService fixUpTaskService;

	// Constructor----------------------------------------------

	public PhaseService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Phase create(final int fixUpTaskId) {
		final Phase phase = new Phase();
		// final FixUpTask fixUpTask =
		// this.fixUpTaskService.findOne(fixUpTaskId);
		// phase.setFixUpTask(fixUpTask);
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
		final Phase saved = this.phaseRepository.save(phase);
		return saved;
	}

	public void delete(final Phase entity) {
		this.phaseRepository.delete(entity);
	}

	// Other Methods--------------------------------------------

	Collection<Phase> findPhasesByFixUpTaskId(int fixUpTaskId) {
		Collection<Phase> phases = phaseRepository
				.findPhasesByFixUpTaskId(fixUpTaskId);
		return phases;
	}
}
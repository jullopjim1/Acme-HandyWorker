
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Phase;
import repositories.PhaseRepository;

@Service
@Transactional
public class PhaseService {

	//Repository-----------------------------------------------

	@Autowired
	private PhaseRepository phaseRepository;

	//Services-------------------------------------------------


	//Constructor---------------------------------------------- 

	public PhaseService() {
		super();
	}

	//Simple CRUD----------------------------------------------

	public Phase create() {
		final Phase phase = new Phase();

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

	//Other Methods--------------------------------------------

}


package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private EndorsementRepository	endorsementRepository;


	//Services---------------------------------------------------------------------------

	//Constructor------------------------------------------------------------------------

	public EndorsementService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Endorsement create() {
		final Endorsement endorsement = new Endorsement();

		return endorsement;

	}

	public List<Endorsement> findAll() {
		return this.endorsementRepository.findAll();
	}

	public Endorsement findOne(final Integer endorsementId) {
		return this.endorsementRepository.findOne(endorsementId);
	}
	public Endorsement save(final Endorsement endorsement) {
		final Endorsement saved = this.endorsementRepository.save(endorsement);
		return saved;
	}

	public void delete(final Endorsement entity) {
		this.endorsementRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

}

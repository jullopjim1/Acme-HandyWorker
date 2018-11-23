
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.EndorserRepository;
import domain.Endorser;

@Service
@Transactional
public class EndorserService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private EndorserRepository	endorserRepository;


	//Services---------------------------------------------------------------------------

	//Constructor------------------------------------------------------------------------

	public EndorserService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	//TODO create endorser
	//	public Endorser create() {
	//		final Endorser endorser = new Endorser();
	//
	//		return endorser;
	//
	//	}

	public List<Endorser> findAll() {
		return this.endorserRepository.findAll();
	}

	public Endorser findOne(final Integer endorserId) {
		return this.endorserRepository.findOne(endorserId);
	}
	public Endorser save(final Endorser endorser) {
		final Endorser saved = this.endorserRepository.save(endorser);
		return saved;
	}

	public void delete(final Endorser entity) {
		this.endorserRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

}

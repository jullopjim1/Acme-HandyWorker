
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BoxRepository;
import domain.Box;

@Service
@Transactional
public class BoxService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private BoxRepository	boxRepository;


	//Services---------------------------------------------------------------------------

	//Constructor------------------------------------------------------------------------

	public BoxService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Box create() {
		final Box box = new Box();

		return box;

	}

	public List<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final Integer boxId) {
		return this.boxRepository.findOne(boxId);
	}
	public Box save(final Box box) {
		final Box saved = this.boxRepository.save(box);
		return saved;
	}

	public void delete(final Box entity) {
		this.boxRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

}

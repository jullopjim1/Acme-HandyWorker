
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import repositories.CurriculumRepository;
import repositories.HandyWorkerRepository;

@Service
@Transactional
public class CurriculumService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	//Services---------------------------------------------------------------------------


	//Constructor------------------------------------------------------------------------

	public CurriculumService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Curriculum create(final int handyWorkerId) {
		final Curriculum curriculum = new Curriculum();

		//TODO: Ticker
		curriculum.setTicker("");
		curriculum.setHandyWorker(this.handyWorkerRepository.findHandyWorkerById(handyWorkerId));

		return curriculum;

	}

	public List<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public Curriculum findOne(final Integer curriculumId) {
		return this.curriculumRepository.findOne(curriculumId);
	}
	public Curriculum save(final Curriculum curriculum) {
		final Curriculum saved = this.curriculumRepository.save(curriculum);
		return saved;
	}

	public void delete(final Curriculum entity) {
		this.curriculumRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

	public Curriculum findCurriculumById(final int curriculumId) {
		return this.curriculumRepository.findCurriculumById(curriculumId);
	}

}


package services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;
import repositories.CurriculumRepository;

@Service
@Transactional
public class CurriculumService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private CurriculumRepository curriculumRepository;

	//Services---------------------------------------------------------------------------


	//Constructor------------------------------------------------------------------------

	public CurriculumService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Curriculum create() {
		final Curriculum curriculum = new Curriculum();

		//TODO: Ticker
		curriculum.setTicker("");

		final ArrayList<EducationRecord> educationRecords = new ArrayList<>();
		curriculum.setEducationRecord(educationRecords);

		final ArrayList<ProfessionalRecord> professionalRecords = new ArrayList<>();
		curriculum.setProfessionalRecord(professionalRecords);

		final ArrayList<EndorserRecord> endorserRecords = new ArrayList<>();
		curriculum.setEndorserRecord(endorserRecords);

		final ArrayList<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();
		curriculum.setMiscellaneousRecord(miscellaneousRecords);

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

}

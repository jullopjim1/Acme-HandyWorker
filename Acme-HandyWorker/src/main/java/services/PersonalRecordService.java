
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;
import repositories.PersonalRecordRepository;

@Service
@Transactional
public class PersonalRecordService {

	//Repository-----------------------------------------------------------------------------

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	//Services-------------------------------------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;


	//Constructor----------------------------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	//Simply CRUD----------------------------------------------------------------------------

	public PersonalRecord create(final int curriculumId) {
		final PersonalRecord personalRecord = new PersonalRecord();
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);
		final HandyWorker handyWorker = curriculum.getHandyWorker();

		personalRecord.setFullName(handyWorker.getName() + " " + handyWorker.getSurname());
		personalRecord.setCurriculum(curriculum);
		personalRecord.setEmail(handyWorker.getEmail());
		personalRecord.setPhone(handyWorker.getPhone());
		personalRecord.setPhoto(handyWorker.getPhoto());

		return personalRecord;
	}

	public List<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final Integer id) {
		return this.personalRecordRepository.findOne(id);
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		return this.personalRecordRepository.save(personalRecord);
	}

	public void delete(final PersonalRecord entity) {
		this.personalRecordRepository.delete(entity);
	}

	public PersonalRecord findPersonalRecordByCurriculumId(final int curriculumId) {
		return this.personalRecordRepository.findPersonalRecordByCurriculumId(curriculumId);
	}

}

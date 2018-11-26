
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;
import repositories.PersonalRecordRepository;
import security.LoginService;

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
		Assert.notNull(personalRecord);
		this.checkPrincipal(personalRecord);
		final PersonalRecord saved = this.personalRecordRepository.save(personalRecord);
		return saved;
	}

	public void delete(final PersonalRecord personalRecord) {
		this.checkPrincipal(personalRecord);
		this.personalRecordRepository.delete(personalRecord);
	}

	//Other Methods------------------------------------------------------------------

	public PersonalRecord findPersonalRecordByCurriculumId(final int curriculumId) {
		return this.personalRecordRepository.findPersonalRecordByCurriculumId(curriculumId);
	}

	public Boolean checkPrincipal(final PersonalRecord personalRecord) {
		final HandyWorker handyWorker = personalRecord.getCurriculum().getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

}

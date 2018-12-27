
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.EducationRecord;
import domain.HandyWorker;
import repositories.EducationRecordRepository;
import security.LoginService;

@Service
@Transactional
public class EducationRecordService {

	//Repository-----------------------------------------------------------------------------

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	//Services-------------------------------------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;


	//Constructor----------------------------------------------------------------------------

	public EducationRecordService() {
		super();
	}

	//Simply CRUD----------------------------------------------------------------------------

	public EducationRecord create(final int curriculumId) {
		final EducationRecord educationRecord = new EducationRecord();
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		educationRecord.setCurriculum(curriculum);

		return educationRecord;
	}

	public List<EducationRecord> findAll() {
		return this.educationRecordRepository.findAll();
	}

	public EducationRecord findOne(final Integer id) {
		return this.educationRecordRepository.findOne(id);
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		this.checkPrincipal(educationRecord);
		Assert.isTrue(educationRecord.getStartMoment().before(educationRecord.getEndMoment()), "date.commit.error");
		final EducationRecord saved = this.educationRecordRepository.save(educationRecord);
		return saved;
	}

	public void delete(final EducationRecord educationRecord) {
		this.checkPrincipal(educationRecord);
		this.educationRecordRepository.delete(educationRecord);
	}

	//Other Methods------------------------------------------------------------------

	public Collection<EducationRecord> findEducationRecordByCurriculumId(final int curriculumId) {
		return this.educationRecordRepository.findEducationRecordByCurriculumId(curriculumId);
	}

	public Boolean checkPrincipal(final EducationRecord educationRecord) {
		final HandyWorker handyWorker = educationRecord.getCurriculum().getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

}

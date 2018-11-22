
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import domain.EducationRecord;
import repositories.EducationRecordRepository;

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
		return this.educationRecordRepository.save(educationRecord);
	}

	public void delete(final EducationRecord entity) {
		this.educationRecordRepository.delete(entity);
	}

	public EducationRecord findEducationRecordByCurriculumId(final int curriculumId) {
		return this.educationRecordRepository.findEducationRecordByCurriculumId(curriculumId);
	}

}

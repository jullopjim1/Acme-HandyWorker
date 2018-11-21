
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import domain.ProfessionalRecord;
import repositories.ProfessionalRecordRepository;

@Service
@Transactional
public class ProfessionalRecordService {

	//Repository-----------------------------------------------------------------------------

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	//Services-------------------------------------------------------------------------------

	@Autowired
	private CurriculumService				curriculumService;


	//Constructor----------------------------------------------------------------------------

	public ProfessionalRecordService() {
		super();
	}

	//Simply CRUD----------------------------------------------------------------------------

	public ProfessionalRecord create(final int curriculumId) {
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		professionalRecord.setCurriculum(curriculum);

		return professionalRecord;
	}

	public List<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(final Integer id) {
		return this.professionalRecordRepository.findOne(id);
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		return this.professionalRecordRepository.save(professionalRecord);
	}

	public void delete(final ProfessionalRecord entity) {
		this.professionalRecordRepository.delete(entity);
	}

}

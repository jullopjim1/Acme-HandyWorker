
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;
import repositories.ProfessionalRecordRepository;
import security.LoginService;

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
		Assert.notNull(professionalRecord);
		this.checkPrincipal(professionalRecord);
		final ProfessionalRecord saved = this.professionalRecordRepository.save(professionalRecord);
		return saved;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		this.checkPrincipal(professionalRecord);
		this.professionalRecordRepository.delete(professionalRecord);
	}

	//Other Methods------------------------------------------------------------------

	public Collection<ProfessionalRecord> findProfessionalRecordByCurriculumId(final int curriculumId) {
		return this.professionalRecordRepository.findProfessionalRecordByCurriculumId(curriculumId);
	}

	public Boolean checkPrincipal(final ProfessionalRecord professionalRecord) {
		final HandyWorker handyWorker = professionalRecord.getCurriculum().getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

}


package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;
import repositories.EndorserRecordRepository;
import security.LoginService;

@Service
@Transactional
public class EndorserRecordService {

	//Repository-----------------------------------------------------------------------------

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	//Services-------------------------------------------------------------------------------

	@Autowired
	private CurriculumService			curriculumService;


	//Constructor----------------------------------------------------------------------------

	public EndorserRecordService() {
		super();
	}

	//Simply CRUD----------------------------------------------------------------------------

	public EndorserRecord create(final int curriculumId) {
		final EndorserRecord endorserRecord = new EndorserRecord();
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		endorserRecord.setCurriculum(curriculum);

		return endorserRecord;
	}

	public List<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	public EndorserRecord findOne(final Integer id) {
		return this.endorserRecordRepository.findOne(id);
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		this.checkPrincipal(endorserRecord);
		final EndorserRecord saved = this.endorserRecordRepository.save(endorserRecord);
		return saved;
	}

	public void delete(final EndorserRecord endorserRecord) {
		this.checkPrincipal(endorserRecord);
		this.endorserRecordRepository.delete(endorserRecord);
	}

	//Other Methods------------------------------------------------------------------

	public Collection<EndorserRecord> findEndorserRecordByCurriculumId(final int curriculumId) {
		return this.endorserRecordRepository.findEndorserRecordByCurriculumId(curriculumId);
	}

	public Boolean checkPrincipal(final EndorserRecord endorserRecord) {
		final HandyWorker handyWorker = endorserRecord.getCurriculum().getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

}

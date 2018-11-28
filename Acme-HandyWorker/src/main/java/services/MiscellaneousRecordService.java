
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import repositories.MiscellaneousRecordRepository;
import security.LoginService;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Repository-----------------------------------------------------------------------------

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	//Services-------------------------------------------------------------------------------

	@Autowired
	private CurriculumService				curriculumService;


	//Constructor----------------------------------------------------------------------------

	public MiscellaneousRecordService() {
		super();
	}

	//Simply CRUD----------------------------------------------------------------------------

	public MiscellaneousRecord create(final int curriculumId) {
		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		final Curriculum curriculum = this.curriculumService.findOne(curriculumId);

		miscellaneousRecord.setCurriculum(curriculum);

		return miscellaneousRecord;
	}

	public List<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(final Integer id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		this.checkPrincipal(miscellaneousRecord);
		final MiscellaneousRecord saved = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		return saved;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		this.checkPrincipal(miscellaneousRecord);
		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	//Other Methods------------------------------------------------------------------

	public Collection<MiscellaneousRecord> findMiscellaneousRecordByCurriculumId(final int curriculumId) {
		return this.miscellaneousRecordRepository.findMiscellaneousRecordByCurriculumId(curriculumId);
	}

	public Boolean checkPrincipal(final MiscellaneousRecord miscellaneousRecord) {
		final HandyWorker handyWorker = miscellaneousRecord.getCurriculum().getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

}

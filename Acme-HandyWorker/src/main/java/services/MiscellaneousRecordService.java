
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import domain.MiscellaneousRecord;
import repositories.MiscellaneousRecordRepository;

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
		final Curriculum curriculum = this.curriculumService.findCurriculumById(curriculumId);

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
		return this.miscellaneousRecordRepository.save(miscellaneousRecord);
	}

	public void delete(final MiscellaneousRecord entity) {
		this.miscellaneousRecordRepository.delete(entity);
	}

}


package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import domain.EndorserRecord;
import repositories.EndorserRecordRepository;

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
		return this.endorserRecordRepository.save(endorserRecord);
	}

	public void delete(final EndorserRecord entity) {
		this.endorserRecordRepository.delete(entity);
	}

	public EndorserRecord findEndorserRecordByCurriculumId(final int curriculumId) {
		return this.endorserRecordRepository.findEndorserRecordByCurriculumId(curriculumId);
	}

}

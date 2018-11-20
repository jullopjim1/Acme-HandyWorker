
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	private LoginService				loginService;


	//Constructor----------------------------------------------------------------------------

	public PersonalRecordService() {
		super();
	}

	//Simply CRUD----------------------------------------------------------------------------

	public PersonalRecord create() {
		final PersonalRecord personalRecord = new PersonalRecord();

		//TODO: Terminar
		personalRecord.setFullName("");

		return personalRecord;
	}

	public List<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final Integer id) {
		return this.personalRecordRepository.findOne(id);
	}

	public <S extends PersonalRecord> List<S> save(final Iterable<S> entities) {
		return this.personalRecordRepository.save(entities);
	}

	public void delete(final PersonalRecord entity) {
		this.personalRecordRepository.delete(entity);
	}

}

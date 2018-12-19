
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;
import domain.Ticker;
import repositories.CurriculumRepository;
import security.LoginService;

@Service
@Transactional
public class CurriculumService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private CurriculumRepository		curriculumRepository;

	//Services---------------------------------------------------------------------------

	@Autowired
	private HandyWorkerService			handyWorkerService;

	@Autowired
	private PersonalRecordService		personalRecordService;

	@Autowired
	private ProfessionalRecordService	professionalRecordService;

	@Autowired
	private EducationRecordService		educationRecordService;

	@Autowired
	private EndorserRecordService		endorserRecordService;

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	private TickerService				tickerService;


	//Constructor------------------------------------------------------------------------

	public CurriculumService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Curriculum create(final int handyWorkerId) {
		final Curriculum curriculum = new Curriculum();

		//Ticker Unico
		final Ticker ticker = this.tickerService.isUniqueTicker();
		final Ticker saved = this.tickerService.save(ticker);

		curriculum.setTicker(saved);
		curriculum.setHandyWorker(this.handyWorkerService.findOne(handyWorkerId));

		return curriculum;

	}

	public List<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public Curriculum findOne(final Integer curriculumId) {
		return this.curriculumRepository.findOne(curriculumId);
	}
	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		this.checkPrincipal(curriculum);
		final Curriculum saved = this.curriculumRepository.save(curriculum);
		return saved;
	}

	public void delete(final Curriculum curriculum) {
		this.checkPrincipal(curriculum);

		final PersonalRecord personalRecord = this.personalRecordService.findPersonalRecordByCurriculumId(curriculum.getId());
		final Collection<EducationRecord> educationRecords = new ArrayList<>(this.educationRecordService.findEducationRecordByCurriculumId(curriculum.getId()));
		final Collection<ProfessionalRecord> professionalRecords = new ArrayList<>(this.professionalRecordService.findProfessionalRecordByCurriculumId(curriculum.getId()));
		final Collection<EndorserRecord> endorserRecords = new ArrayList<>(this.endorserRecordService.findEndorserRecordByCurriculumId(curriculum.getId()));
		final Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>(this.miscellaneousRecordService.findMiscellaneousRecordByCurriculumId(curriculum.getId()));

		this.personalRecordService.delete(personalRecord);

		if (educationRecords.size() != 0 || professionalRecords.size() != 0 || endorserRecords.size() != 0 || miscellaneousRecords.size() != 0) {
			for (final EducationRecord educationRecord : educationRecords)
				this.educationRecordService.delete(educationRecord);

			for (final MiscellaneousRecord miscellaneousRecord : miscellaneousRecords)
				this.miscellaneousRecordService.delete(miscellaneousRecord);

			for (final ProfessionalRecord professionalRecord : professionalRecords)
				this.professionalRecordService.delete(professionalRecord);

			for (final EndorserRecord endorserRecord : endorserRecords)
				this.endorserRecordService.delete(endorserRecord);

		}

		this.curriculumRepository.delete(curriculum);

	}

	//Other Methods---------------------------------------------------------------------------

	@SuppressWarnings("deprecation")
	public String generateTicker() {
		final Date date = new Date();
		final Integer s1 = date.getDate();
		String day = s1.toString();
		if (day.length() == 1)
			day = "0" + day;
		final Integer s2 = date.getMonth() + 1;
		String month = s2.toString();
		if (month.length() == 1)
			month = "0" + month;
		final Integer s3 = date.getYear();
		final String year = s3.toString().substring(1);

		return year + month + day + "-" + CurriculumService.generateStringAux();
	}

	private static String generateStringAux() {
		final int length = 6;
		final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 6; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public Curriculum findCurriculumHandyWorkerById(final int handyWorkerId) {
		return this.curriculumRepository.findCurriculumHandyWorkerById(handyWorkerId);
	}

	public Boolean checkPrincipal(final Curriculum curriculum) {
		final HandyWorker handyWorker = curriculum.getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()));
		return true;
	}

	public Boolean hasCurriculum(final HandyWorker handyWorker) {
		Boolean res = false;

		final Curriculum curriculum = this.findCurriculumHandyWorkerById(handyWorker.getId());
		if (curriculum != null)
			res = true;

		return res;
	}

}

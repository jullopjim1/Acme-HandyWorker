
package services;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Curriculum;
import repositories.CurriculumRepository;
import repositories.HandyWorkerRepository;

@Service
@Transactional
public class CurriculumService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private CurriculumRepository	curriculumRepository;

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	//Services---------------------------------------------------------------------------


	//Constructor------------------------------------------------------------------------

	public CurriculumService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Curriculum create(final int handyWorkerId) {
		final Curriculum curriculum = new Curriculum();

		curriculum.setTicker(this.generateTicker());
		curriculum.setHandyWorker(this.handyWorkerRepository.findOne(handyWorkerId));

		return curriculum;

	}

	public List<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public Curriculum findOne(final int curriculumId) {
		return this.curriculumRepository.findOne(curriculumId);
	}
	public Curriculum save(final Curriculum curriculum) {
		final Curriculum saved = this.curriculumRepository.save(curriculum);
		return saved;
	}

	public void delete(final Curriculum entity) {
		this.curriculumRepository.delete(entity);
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
		final String characters = "ABCDEFGHIJKLMN�OPQRSTUVWXYZ1234567890";
		final Random rng = new Random();
		final char[] text = new char[length];
		for (int i = 0; i < 6; i++)
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		return new String(text);
	}

	public Curriculum findCurriculumHandyWorkerById(final int handyWorkerId) {
		return this.curriculumRepository.findCurriculumHandyWorkerById(handyWorkerId);
	}

}

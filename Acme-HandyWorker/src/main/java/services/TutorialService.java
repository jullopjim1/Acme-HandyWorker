
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import repositories.TutorialRepository;
import domain.Section;
import domain.Tutorial;

@Service
public class TutorialService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private TutorialRepository		tutorialRepository;
	private HandyWorkerRepository	handyWorkerRepository;


	//Constructor----------------------------------------------------------------------------

	public TutorialService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Tutorial create(final int handyWorkerId) {
		final Tutorial tutorial = new Tutorial();
		tutorial.setHandyWorker(this.handyWorkerRepository.findOne(handyWorkerId));
		final Collection<Section> p = new ArrayList<>();
		tutorial.setSections(p);
		tutorial.setMoment(new Date(System.currentTimeMillis() - 1000));
		return tutorial;
	}

	public Collection<Tutorial> findAll() {
		Collection<Tutorial> tutorials;

		tutorials = this.tutorialRepository.findAll();
		Assert.notNull(tutorials);

		return tutorials;
	}
	public Tutorial findOne(final int TutorialID) {
		Tutorial tutorial;
		tutorial = this.tutorialRepository.findOne(TutorialID);
		Assert.notNull(TutorialID);

		return tutorial;
	}

	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);
		Tutorial result;

		result = this.tutorialRepository.save(tutorial);

		return result;
	}
	public void delete(final Tutorial tutorial) {

		Assert.notNull(tutorial);
		this.tutorialRepository.delete(tutorial);
	}

}

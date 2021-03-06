
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import repositories.TutorialRepository;
import security.LoginService;
import domain.HandyWorker;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private TutorialRepository		tutorialRepository;

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	//Constructor----------------------------------------------------------------------------

	public TutorialService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Tutorial create(final int handyWorkerId) {
		final Tutorial tutorial = new Tutorial();
		tutorial.setHandyWorker(this.handyWorkerRepository.findOne(handyWorkerId));
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
		this.checkPrincipal(tutorial);
		Assert.notNull(tutorial);

		Tutorial result;

		result = this.tutorialRepository.save(tutorial);

		return result;
	}
	public void delete(final Tutorial tutorial) {
		this.checkPrincipal(tutorial);
		Assert.notNull(tutorial);
		this.tutorialRepository.delete(tutorial);
	}

	//Other Methods-----------------------------------------------------------------
	public Collection<Tutorial> findTutorialsByHandyWorkerId(final int handyWorkerId) {
		return this.tutorialRepository.findTutorialsByHandyWorkerId(handyWorkerId);
	}
	public Boolean checkPrincipal(final Tutorial tutorial) {
		final HandyWorker handyWorker = tutorial.getHandyWorker();
		Assert.isTrue(handyWorker.getUserAccount().equals(LoginService.getPrincipal()), "este tutorial no corresponde con el handyworker que lo creo");
		return true;
	}
}

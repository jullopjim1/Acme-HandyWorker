package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.UserAccount;
import domain.Application;
import domain.HandyWorker;

@Service
@Transactional
public class HandyWorkerService {

	//Repository-----------------------------------------------

	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;


	//Services-------------------------------------------------
	//Constructor----------------------------------------------

	public HandyWorkerService() {

		super();
	}

	//Simple CRUD----------------------------------------------

	public HandyWorker create() {
		final HandyWorker handyWorker =  new HandyWorker();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();
		final Authority a = new Authority();
		a.setAuthority("HANDY");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		final Collection<Application> applications = new ArrayList<Application>();
		handyWorker.setUserAccount(userAccount);
		handyWorker.setApplications(applications);
		handyWorker.setIsBanned(false);
		handyWorker.setIsSuspicious(false);
		
		return handyWorker;
	}
	
	public List<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();
	}

	public HandyWorker findOne(final Integer handyWorkerId) {
		return this.handyWorkerRepository.findOne(handyWorkerId);
	}

	public HandyWorker save(final HandyWorker handyWorker) {
		Assert.notNull(handyWorker);
		if(handyWorker.getMake() == null){
			handyWorker.setMake(handyWorker.getSurname()+ " " + handyWorker.getMiddleName() + " " + handyWorker.getName());
		}
		final HandyWorker saved = this.handyWorkerRepository.save(handyWorker);
		return saved;
	}
	public void delete(final HandyWorker entity) {
		this.handyWorkerRepository.delete(entity);
	}

	//Other Methods--------------------------------------------

}
package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import domain.Application;

@Service
@Transactional
public class ApplicationService {

	//Repository-----------------------------------------------
	@Autowired
	private ApplicationRepository	applicationRepository;
	//Services-------------------------------------------------

	//Constructor----------------------------------------------
	public ApplicationService() {
		super();
	}
	//Simple CRUD----------------------------------------------

	public Application create() {
		final Application application = new Application();
		application.setMoment(new Date(System.currentTimeMillis() - 1000));
		return application;
	}

	public List<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final Integer applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application application) {
		Assert.notNull(application);
		application.setMoment(new Date(System.currentTimeMillis() - 1000));
		final Application saved = this.applicationRepository.save(application);
		return saved;
	}

	public void delete(final Application entity) {
		this.applicationRepository.delete(entity);

	}
	//Other Methods--------------------------------------------

}
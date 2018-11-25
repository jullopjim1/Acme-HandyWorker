
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import repositories.FinderRepository;
import domain.Configuration;
import domain.Finder;
import domain.FixUpTask;

@Service
@Transactional
public class FinderService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private FinderRepository		finderRepository;

	//Services---------------------------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;


	//Constructor------------------------------------------------------------------------

	public FinderService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Finder create() {
		final Finder finder = new Finder();

		final Date lastUpdate = new Date();
		final Collection<FixUpTask> fixUpTasks = new ArrayList<>();

		finder.setKeyword("");
		finder.setNamecategory("");
		finder.setNamewarranty("");
		finder.setPriceMax(0.0);
		finder.setPriceMin(0.0);
		//finder.setDateMin();
		//finder.setDateMax();
		finder.setLastUpdate(lastUpdate);
		finder.setFixUpTasks(fixUpTasks);

		return finder;
	}
	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer finderId) {
		return this.finderRepository.findOne(finderId);
	}
	public Finder save(final Finder finder) {
		final Finder saved = this.finderRepository.save(finder);
		return saved;
	}

	public void delete(final Finder entity) {
		this.finderRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

	public Finder updateFinder(final Finder finder) {

		Finder result = finder;

		Collection<Configuration> configurations = null;
		try {

			configurations = this.configurationService.findAll();

		} catch (final Exception e) {
			System.out.println(e);
		}
		Configuration configuration = null;

		for (final Configuration configuration2 : configurations)
			configuration = configuration2;

		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - configuration.getFinderCacheTime() * 1000 * 60 * 60);

		if (!finder.getLastUpdate().after(updateFinder)) {
			finder.setFixUpTasks(this.searchFixUpTask(finder, configuration.getFinderMaxResults()));
			result = this.save(finder);
		}

		return result;
	}
	public Collection<FixUpTask> searchFixUpTask(final Finder f, final int maxResult) {
		final Collection<FixUpTask> result;

		final Date currentDate = new Date();

		final String keyword = "";
		final String namecategory = "";
		final Double priceMin = 0.0;
		final Double priceMax = 9999999.9;
		final Date dateMin = currentDate;
		final Date dateMax = new Date(currentDate.getTime() + 315360000000L); // 315360000000L son 10 años en milisegundos
		final String namewarranty = "";

		result = this.finderRepository.searchFixUpTasks(keyword, dateMin, dateMax, priceMin, priceMax, namecategory, namewarranty, new PageRequest(0, maxResult)).getContent();

		return result;
	}

	public Finder findFinderByHandyWorkerId(final int handyWorkerId) {
		return this.finderRepository.findByHandyWorker(handyWorkerId);
	}

}

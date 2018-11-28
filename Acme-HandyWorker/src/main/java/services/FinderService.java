
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import repositories.FinderRepository;
import utilities.internal.SchemaPrinter;
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
		finder.setPriceMax(999999999999999999.9);
		finder.setPriceMin(0.0);

		finder.setLastUpdate(lastUpdate);
		finder.setFixUpTasks(fixUpTasks);

		final Date current = new Date();
		finder.setDateMin(current);
		finder.setDateMax(new Date(current.getTime() + 315360000000L));

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

		Finder result = this.checkPrincipal(finder);

		final Configuration configuration = this.configurationService.findAll().iterator().next();

		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - configuration.getFinderCacheTime() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(currentDate.getTime() - 1000);

		if (!finder.getLastUpdate().after(updateFinder)) {
			result.setFixUpTasks(this.searchFixUpTask(finder, configuration.getFinderMaxResults()));
			result.setLastUpdate(lastUpdate);
			SchemaPrinter.print(result);
			result = this.finderRepository.save(result);
		}

		return result;
	}

	private Finder checkPrincipal(final Finder f) {
		Finder result;

		final Date currentDate = new Date();

		if (f.getKeyword() == null)
			f.setKeyword("");

		if (f.getNamecategory() == null)
			f.setNamecategory("");

		if (f.getNamewarranty() == null)
			f.setNamewarranty("");

		if (f.getPriceMax() == null)
			f.setPriceMax(9999999.9);

		if (f.getPriceMin() == null)
			f.setPriceMin(0.0);

		if (f.getDateMin() == null)
			f.setDateMin(currentDate);

		if (f.getDateMax() == null)
			f.setDateMax(new Date(currentDate.getTime() + 315360000000L));// 315360000000L son 10 años en milisegundos

		result = f;

		return result;
	}

	public Collection<FixUpTask> searchFixUpTask(final Finder f, final int maxResult) {
		final Collection<FixUpTask> result;

		//final Page<FixUpTask> p = this.finderRepository.searchFixUpTasksf.getPriceMax(), new PageRequest(0, 2));

		final Page<FixUpTask> p = this.finderRepository.searchFixUpTasks(f.getKeyword(), f.getDateMin(), f.getDateMin(), f.getPriceMin(), f.getPriceMax(), f.getNamecategory(), f.getNamewarranty(), new PageRequest(0, maxResult));

		System.out.println(p);
		result = new ArrayList<>(p.getContent());
		System.out.println(result);

		return result;
	}
	public Finder findFinderByHandyWorkerId(final int handyWorkerId) {
		return this.finderRepository.findByHandyWorker(handyWorkerId);
	}

}

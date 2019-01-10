
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Configuration;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;
import repositories.FinderRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class FinderService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private FinderRepository		finderRepository;

	//Services---------------------------------------------------------------------------
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	//Constructor------------------------------------------------------------------------

	public FinderService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Finder create() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final Authority handyAuthority = new Authority();
		handyAuthority.setAuthority("HANDY");
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(handyAuthority), "Solo los handyWorker tiene finder");

		final Finder finder = new Finder();

		final Date lastUpdate = new Date();
		final Collection<FixUpTask> fixUpTasks = new ArrayList<>();

		finder.setKeyword("");
		finder.setNamecategory("");
		finder.setNamewarranty("");
		finder.setPriceMax(9999999.9);
		finder.setPriceMin(0.0);

		finder.setLastUpdate(lastUpdate);
		finder.setFixUpTasks(fixUpTasks);

		final Date current = new Date();
		finder.setDateMin(current);
		finder.setDateMax(new Date(current.getTime() + 315360000000L * 2));

		return finder;
	}
	public List<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder findOne(final Integer finderId) {
		final Finder finder = this.finderRepository.findOne(finderId);
		this.check(finder);

		return finder;
	}
	public Finder save(Finder finder) {
		this.check(finder);

		finder.setLastUpdate(this.updateTime());

		finder = this.updateFinder(finder);

		final Finder saved = this.finderRepository.save(this.updateFinder(finder));
		if (finder.getId() == 0) {
			final UserAccount userAccount = LoginService.getPrincipal();
			final HandyWorker handy = this.handyWorkerService.findHandyWorkerByUserAccount(userAccount.getId());
			handy.setFinder(saved);
			this.handyWorkerService.save(handy);
		}
		return saved;
	}
	/**
	 * No se puede borrar un finder
	 */
	//	public void delete(final Finder entity) {
	//
	//	}

	//Other Methods---------------------------------------------------------------------------

	public Finder updateFinder(final Finder finder) {
		this.check(finder);
		final Finder result = this.checkPrincipal(finder);

		final Configuration configuration = this.configurationService.findAll().iterator().next();

		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - configuration.getFinderCacheTime() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(currentDate.getTime() - 1000);

		if (!finder.getLastUpdate().after(updateFinder)) {
			result.setFixUpTasks(this.searchFixUpTask(finder, configuration.getFinderMaxResults()));
			result.setLastUpdate(lastUpdate);
			//result = this.finderRepository.save(result);
		}

		return result;
	}

	private Date updateTime() {
		final Date currentDate = new Date();
		final Date updateFinder = new Date(currentDate.getTime() - this.configurationService.findOne().getFinderCacheTime() * 1000 * 60 * 60);
		final Date lastUpdate = new Date(updateFinder.getTime() - 1000);

		return lastUpdate;
	}

	private Finder checkPrincipal(final Finder f) {
		Finder result;

		final Date currentDate = new Date();

		if (f.getKeyword() == null)
			f.setKeyword("");

		if (f.getNamewarranty() == null)
			f.setNamewarranty("");

		if (f.getPriceMax() == null)
			f.setPriceMax(9999999.9);

		if (f.getPriceMin() == null)
			f.setPriceMin(0.0);

		if (f.getDateMin() == null)
			f.setDateMin(currentDate);

		if (f.getDateMax() == null)
			f.setDateMax(new Date(currentDate.getTime() + 315360000000L * 2));// 315360000000L son 10 años en milisegundos

		result = f;

		return result;
	}
	public Collection<FixUpTask> searchFixUpTask(final Finder f, final int maxResult) {
		List<FixUpTask> result = new ArrayList<>();
		final String nameCategory = f.getNamecategory();
		final String langCategory = LocaleContextHolder.getLocale().getLanguage().toUpperCase();
		final Finder finder = this.checkPrincipal(f);

		Page<FixUpTask> p;
		if (nameCategory == null || nameCategory.equals(""))
			p = this.finderRepository.searchFixUpTasks(finder.getKeyword(), finder.getDateMin(), finder.getDateMax(), finder.getPriceMin(), finder.getPriceMax(), finder.getNamewarranty(), new PageRequest(0, maxResult));
		else
			p = this.finderRepository.searchFixUpTasks(finder.getKeyword(), finder.getDateMin(), finder.getDateMax(), finder.getPriceMin(), finder.getPriceMax(), langCategory, nameCategory, finder.getNamewarranty(), new PageRequest(0, maxResult));

		if (p.getContent() != null)
			result = new ArrayList<>(p.getContent());

		return result;
	}
	public Finder findFinderByHandyWorkerId(final int handyWorkerId) {
		return this.finderRepository.findByHandyWorker(handyWorkerId);
	}

	private void check(final Finder f) {
		final UserAccount userAccount = LoginService.getPrincipal();
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final Authority handyAuthority = new Authority();
		handyAuthority.setAuthority("HANDY");

		final Finder finder = this.findFinderByHandyWorkerId(actor.getId());

		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(handyAuthority), "Solo los handyWorker tiene finder");

		Assert.isTrue(f.equals(finder) || (f.getId() == 0 && finder == null), "Un finder solo puede ser modificado por su dueño");

	}

	public Finder findFinder() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final HandyWorker handyWorker = this.handyWorkerService.findHandyWorkerByUserAccount(userAccount.getId());

		Finder finder = this.findFinderByHandyWorkerId(handyWorker.getId());

		if (finder == null)
			finder = this.save(this.create());

		return finder;

	}
}

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.LoginService;
import domain.Actor;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	// Repository-----------------------------------------------
	@Autowired
	private CategoryRepository categoryRepository;

	// Services-------------------------------------------------
	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private ActorService actorService;

	// Constructor----------------------------------------------
	public CategoryService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Category create() {
		final Category category = new Category();

		final Collection<Category> subcategories = new ArrayList<Category>();
		category.setSubcategories(subcategories);
		return category;
	}

	public List<Category> findAll() {
		return this.categoryRepository.findAll();
	}

	public Category findOne(final Integer categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public Category save(final Category category) {
		Assert.notNull(category, "CATEGORY A CREAR/EDITAR NO PUEDE SER NULL");

		// ASIGNAR A ROOT SI NO TIENE PADRE
		if (category.getRootcategory() == null)
			category.setRootcategory(this.categoryRepository.findRootCategory());

		// SI HA CAMBIADO DE PADRE LO QUITO DEL ANTIGUO
		if (category.getId() != 0) {
			Category antiguo = this.findOne(category.getId());
			Assert.notNull(antiguo,"ANTIGUO CATEGORY ES NULL");
			if (antiguo.getRootcategory().getId() != category.getRootcategory().getId()) {
				Category padreAnterior = antiguo.getRootcategory();
				Collection<Category> hijosAnteriores = padreAnterior
						.getSubcategories();
				if (hijosAnteriores.contains(antiguo)) {
					hijosAnteriores.remove(antiguo);
					padreAnterior.setSubcategories(hijosAnteriores);
					this.save(padreAnterior);
				}
			}
		}

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService
				.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString()
				.contains("ADMIN"))
			Assert.notNull(null, "SOLO PUEDE CREAR/EDITAR CATEGORY ADMIN");

		// GUARDO CATEGORY
		final Category saved = this.categoryRepository.save(category);

		// ASIGNO A HIJOS ESTE CATEGORY
		// Miro si ya esta en el padre y lo añado
		Category padre = saved.getRootcategory();
		Collection<Category> hijosDelPadre = padre.getSubcategories();
		if (!hijosDelPadre.contains(saved)) {
			hijosDelPadre.add(saved);
			padre.setSubcategories(hijosDelPadre);
			this.save(padre);
		}

		return saved;
	}

	public void delete(final Category category) {
		Assert.notNull(category, "CATEGORY A BORRAR NO PUEDE SER NULL");
		Assert.isTrue(category.getRootcategory() != null,
				"NO SE PUEDE BORRAR CATEGORY ROOT");

		// COJO ACTOR ACTUAL
		final Actor actorActual = this.actorService
				.findActorByUsername(LoginService.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString()
				.contains("ADMIN"))
			Assert.notNull(null, "SOLO PUEDE BORRAR CATEGORY ADMIN");

		// ASIGNAR CATEGORY PADRE A FIXUPTASK
		final Collection<FixUpTask> fixUpTasks = this.fixUpTaskService
				.findTasksByCategoryId(category.getId());
		if (!fixUpTasks.isEmpty())
			for (final FixUpTask f : fixUpTasks) {
				f.setCategory(category.getRootcategory());
				this.fixUpTaskService.save(f);
			}

		// BORRO CATEGORY
		this.categoryRepository.delete(category);

	}

	// Other Methods--------------------------------------------

	// public Category findCategoryByName(final String categoryName) {
	// final Category category =
	// this.categoryRepository.findCategoryByName(categoryName);
	// return category;
	// }

	public Collection<Category> categoryTreeToPlain() {
		final Collection<Category> result = new LinkedList<>();

		final Category category = this.categoryRepository.findRootCategory();
		result.add(category);
		for (final Category c : category.getSubcategories())
			if (!c.getSubcategories().isEmpty())
				result.addAll(this.findDescendant(c));
			else
				result.add(c);

		return result;
	}

	private Collection<Category> findDescendant(final Category father) {
		final Collection<Category> result = new LinkedList<>();

		final Collection<String> keys = father.getName().keySet();
		result.add(father);
		for (final Category c : father.getSubcategories()) {

			for (final String key : keys) {
				final String value = father.getName().get(key) + "/"
						+ c.getName().get(key);
				c.getName().remove(key);
				c.getName().put(key, value);
			}

			if (!c.getSubcategories().isEmpty())
				result.addAll(this.findDescendant(c));
			else
				result.add(c);
		}
		return result;
	}

	public Category findRootCategory() {
		return this.categoryRepository.findRootCategory();
	}
}

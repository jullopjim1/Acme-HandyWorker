package services;

import java.util.ArrayList;
import java.util.Collection;
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
		
		//ASIGNAR A ROOT SI NO TIENE PADRE
		if(category.getRootcategory()==null) {
			category.setRootcategory(this.findCategoryByName("rootCategory"));
		}

		// COJO ACTOR ACTUAL
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString()
				.contains("ADMIN")) {
			Assert.notNull(null, "SOLO PUEDE CREAR/EDITAR CATEGORY ADMIN");
		}

		// GUARDO CATEGORY
		final Category saved = this.categoryRepository.save(category);
		return saved;
	}

	public void delete(final Category category) {
		Assert.notNull(category, "CATEGORY A BORRAR NO PUEDE SER NULL");
		Assert.isTrue(category.getRootcategory() != null, "NO SE PUEDE BORRAR CATEGORY ROOT");

		// COJO ACTOR ACTUAL
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString()
				.contains("ADMIN")) {
			Assert.notNull(null, "SOLO PUEDE BORRAR CATEGORY ADMIN");
		}

		// ASIGNAR CATEGORY PADRE A FIXUPTASK
		Collection<FixUpTask> fixUpTasks = fixUpTaskService
				.findTasksByCategoryId(category.getId());
		if (!fixUpTasks.isEmpty()) {
			for (FixUpTask f : fixUpTasks) {
				f.setCategory(category.getRootcategory());
				fixUpTaskService.save(f);
			}
		}
		
		//BORRO CATEGORY
		this.categoryRepository.delete(category);

	}

	// Other Methods--------------------------------------------

	public Category findCategoryByName(String categoryName) {
		Category category = categoryRepository.findCategoryByName(categoryName);
		return category;
	}

}
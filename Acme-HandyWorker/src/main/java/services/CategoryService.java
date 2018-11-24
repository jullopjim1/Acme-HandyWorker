package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
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
		Assert.notNull(category);
		final Category saved = this.categoryRepository.save(category);
		return saved;
	}

	public void delete(final Category category) {
		// ASIGNAR CATEGORY
		Collection<FixUpTask> fixUpTasks = fixUpTaskService.findTasksByCategoryId(category.getId());
		if (!fixUpTasks.isEmpty()) {
			for (FixUpTask f : fixUpTasks) {
				f.setCategory(category.getRootcategory());
				fixUpTaskService.save(f);
			}
		}
		this.categoryRepository.delete(category);

	}

	// Other Methods--------------------------------------------

	public Category findCategoryByName(String categoryName) {
		Category category = categoryRepository.findCategoryByName(categoryName);
		return category;
	}

}

package service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.CategoryService;
import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private CategoryService	categoryService;


	// Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		// LOGIN COMO ADMIN
		this.authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = this.categoryService.create();
		final Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		final Category root = this.categoryService.findOne(this.getEntityId("rootCategory"));
		category.setRootcategory(root);
	}

	@Test
	public void testSave() {
		// LOGIN COMO ADMIN
		this.authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = this.categoryService.create();
		final Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		final Category root = this.categoryService.findOne(this.getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		this.categoryService.save(category);
	}

	@Test
	public void testFind() {
		// LOGIN COMO ADMIN
		this.authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = this.categoryService.create();
		final Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		final Category root = this.categoryService.findOne(this.getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		final Category saved = this.categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(this.categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(this.categoryService.findOne(saved.getId()) == saved);
	}

	@Test
	public void testEdit() {
		// LOGIN COMO ADMIN
		this.authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = this.categoryService.create();
		final Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		final Category root = this.categoryService.findOne(this.getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		final Category saved = this.categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(this.categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(this.categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		name.put("en", "category2");
		name.put("es", "category2");

		category.setName(name);
		final Category saved2 = this.categoryService.save(saved);
		Assert.isTrue(this.categoryService.findOne(saved2.getId()).getName().values().contains("category"));
	}

	@Test
	public void testDelete() {
		// LOGIN COMO ADMIN
		this.authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = this.categoryService.create();
		final Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		final Category root = this.categoryService.findOne(this.getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		final Category saved = this.categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(this.categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(this.categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		saved.setName(name);
		final Category saved2 = this.categoryService.save(saved);
		Assert.isTrue(this.categoryService.findOne(saved2.getId()).getName().values().contains("category"));

		// BORRO CATEGORY
		this.categoryService.delete(saved);
		Assert.isTrue(!this.categoryService.findAll().contains(saved));
		this.unauthenticate();
	}

	@Test
	public void testCategoryTreeToPlain() {
		final Collection<Category> c = this.categoryService.findAll();

		final int size = c.size();

		final Collection<Category> categories = this.categoryService.categoryTreeToPlain();

		for (final Category category : categories)
			System.out.println(category.getName());

		Assert.isTrue(categories.size() == size, "ERROR en tamaño de la lista");

	}

}

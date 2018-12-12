package service;

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
@ContextConfiguration(locations = { "classpath:spring/datasource.xml", "classpath:spring/config/packages.xml" })
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private CategoryService categoryService;

	// Test-------------------------------------------------------------

	@Test
	public void testCreate() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		Category root = categoryService.findOne(getEntityId("rootCategory"));
		category.setRootcategory(root);
	}

	@Test
	public void testSave() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		Category root = categoryService.findOne(getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		categoryService.save(category);
	}

	@Test
	public void testFind() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		Category root = categoryService.findOne(getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		Category saved = categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(categoryService.findOne(saved.getId()) == saved);
	}

	@Test
	public void testEdit() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		Category root = categoryService.findOne(getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		Category saved = categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		name.put("en", "category2");
		name.put("es", "category2");

		category.setName(name);
		Category saved2 = categoryService.save(saved);
		Assert.isTrue(categoryService.findOne(saved2.getId()).getName().values().contains("category"));
	}

	@Test
	public void testDelete() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		Map<String, String> name = new HashMap<>();
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		Category root = categoryService.findOne(getEntityId("rootCategory"));
		category.setRootcategory(root);

		// GUARDO CATEGORY
		Category saved = categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		name.put("en", "category");
		name.put("es", "category");

		category.setName(name);
		saved.setName(name);
		Category saved2 = categoryService.save(saved);
		Assert.isTrue(categoryService.findOne(saved2.getId()).getName().values().contains("category"));

		// BORRO CATEGORY
		categoryService.delete(saved);
		Assert.isTrue(!categoryService.findAll().contains(saved));
		unauthenticate();
	}
}

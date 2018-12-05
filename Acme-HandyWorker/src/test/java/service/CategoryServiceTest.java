package service;

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
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private CategoryService categoryService;

	// Test-------------------------------------------------------------

	@Test
	public void test() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		category.setName("categoryT1");
		category.setRootcategory(categoryService
				.findCategoryByName("rootCategory"));

		// GUARDO CATEGORY
		Category saved = categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		saved.setName("categoryT1Edit");
		Category saved2 = categoryService.save(saved);
		Assert.isTrue(categoryService.findOne(saved2.getId()).getName() == "categoryT1Edit");

		// BORRO CATEGORY
		categoryService.delete(saved);
		Assert.isTrue(!categoryService.findAll().contains(saved));
		unauthenticate();
	}

	@Test
	public void testCreate() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		category.setName("categoryT1");
		category.setRootcategory(categoryService
				.findCategoryByName("rootCategory"));
	}

	@Test
	public void testSave() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		category.setName("categoryT1");
		category.setRootcategory(categoryService
				.findCategoryByName("rootCategory"));

		// GUARDO CATEGORY
		categoryService.save(category);
	}

	@Test
	public void testFind() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		category.setName("categoryT1");
		category.setRootcategory(categoryService
				.findCategoryByName("rootCategory"));

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
		category.setName("categoryT1");
		category.setRootcategory(categoryService
				.findCategoryByName("rootCategory"));

		// GUARDO CATEGORY
		Category saved = categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		saved.setName("categoryT1Edit");
		Category saved2 = categoryService.save(saved);
		Assert.isTrue(categoryService.findOne(saved2.getId()).getName() == "categoryT1Edit");
	}

	@Test
	public void testDelete() {
		// LOGIN COMO ADMIN
		authenticate("admin");

		// CREO CATEGORY Y SETEO VALORES
		final Category category = categoryService.create();
		category.setName("categoryT1");
		category.setRootcategory(categoryService
				.findCategoryByName("rootCategory"));

		// GUARDO CATEGORY
		Category saved = categoryService.save(category);

		// CHECK FINDALL
		Assert.isTrue(categoryService.findAll().contains(saved));

		// CHECK FINDONE
		Assert.isTrue(categoryService.findOne(saved.getId()) == saved);

		// EDITO VALOR Y GUARDO CATEGORY EDITADA
		saved.setName("categoryT1Edit");
		Category saved2 = categoryService.save(saved);
		Assert.isTrue(categoryService.findOne(saved2.getId()).getName() == "categoryT1Edit");

		// BORRO CATEGORY
		categoryService.delete(saved);
		Assert.isTrue(!categoryService.findAll().contains(saved));
		unauthenticate();
	}
}

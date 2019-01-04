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
public class CategoryServiceTest2 extends AbstractTest {

	// Service----------------------------------------------------------
	@Autowired
	private CategoryService categoryService;

	// Test-------------------------------------------------------------

	@Test
	public void test() {
		// LOGIN COMO ADMIN
		this.authenticate("admin");

		final int categoryId = this.getEntityId("category5");
		final int categoryId2 = this.getEntityId("category4");
		Category category = categoryService.findOne(categoryId);
		Category antiguoPadre = categoryService.findOne(category.getRootcategory().getId());
		Category nuevoPadre = categoryService.findOne(categoryId2);
		category.setRootcategory(nuevoPadre);
		Category saved = categoryService.save(category);
		category = categoryService.findOne(categoryId);
		nuevoPadre = categoryService.findOne(categoryId2);
		antiguoPadre = categoryService.findOne(antiguoPadre.getId());
		Assert.isTrue(nuevoPadre.getSubcategories().contains(saved), "Hay que añadir al padre nuevo");
		Assert.isTrue(!antiguoPadre.getSubcategories().contains(saved), "Hay que borrar del padre antiguo");
		
	}

}

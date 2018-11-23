
package service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import services.WarrantyService;
import utilities.AbstractTest;
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	//Service----------------------------------------------------------
	@Autowired
	private WarrantyService	warrantyService;


	//Test-------------------------------------------------------------

	@Test
	public void testWarranty() {
		System.out.println("------Test Warranty------");
		final Warranty warranty, saved;
		final Collection<Warranty> warranties;

		warranty = this.warrantyService.create();

		warranty.setTitle("Garantia de tu puta madre");
		warranty.setTerms("Esto no vale para una mierda jaja salu2");
		warranty.setLaws("Chupala perro");

		saved = this.warrantyService.save(warranty);
		Assert.notNull(saved);
		warranties = this.warrantyService.findAll();

		Assert.isTrue(warranties.contains(saved));
	}
}

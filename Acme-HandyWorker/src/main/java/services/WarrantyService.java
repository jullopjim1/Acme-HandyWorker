
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private WarrantyRepository	warrantyRepository;


	//Services---------------------------------------------------------------------------

	//Constructor------------------------------------------------------------------------

	public WarrantyService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Warranty create() {
		final Warranty warranty = new Warranty();

		warranty.setIsFinal(false);

		return warranty;

	}
	public List<Warranty> findAll() {
		return this.warrantyRepository.findAll();
	}

	public Warranty findOne(final Integer warrantyId) {
		return this.warrantyRepository.findOne(warrantyId);
	}
	public Warranty save(final Warranty warranty) {
		Assert.notNull(warranty);
		final Warranty saved = this.warrantyRepository.save(warranty);
		saved.setIsFinal(true);
		Assert.isTrue(saved.getIsFinal().equals(true));
		return saved;
	}
	public void delete(final Warranty entity) {
		this.warrantyRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------
}

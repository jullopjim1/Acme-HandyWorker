
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
public class SectionService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private SectionRepository	sectionpository;


	//Constructor----------------------------------------------------------------------------

	public SectionService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Section create() {
		final Section section = new Section();

		return section;
	}

	public Collection<Section> findAll() {
		Collection<Section> sections;

		sections = this.sectionpository.findAll();
		Assert.notNull(sections);

		return sections;
	}
	public Section findOne(final int SectionId) {
		Section section;
		section = this.sectionpository.findOne(SectionId);
		Assert.notNull(SectionId);

		return section;
	}

	public Section save(final Section section) {
		Assert.notNull(section);
		Section result;

		result = this.sectionpository.save(section);

		return result;
	}
	public void delete(final Section section) {

		Assert.notNull(section);
		this.sectionpository.delete(section);
	}

}

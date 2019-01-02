
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;
import domain.Tutorial;

@Service
@Transactional
public class SectionService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private SectionRepository	sectionpository;

	//Service-------------------------------------------------------------------------

	@Autowired
	private TutorialService		tutorialService;


	//Constructor----------------------------------------------------------------------------

	public SectionService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Section create(final int tutorialId) {
		final Section section = new Section();
		section.setTutorial(this.tutorialService.findOne(tutorialId));

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
		this.checkPosition(result);
		return result;
	}
	public void delete(final Section section) {

		Assert.notNull(section);
		this.sectionpository.delete(section);
	}

	//Other Methods------------------------------------------------------------------
	public Collection<Section> findSectionByTutorialId(final int tutorialId) {
		return this.sectionpository.findSectionByTutorialId(tutorialId);
	}

	public Boolean checkPosition(final Section section) {
		final Tutorial t = section.getTutorial();
		final Collection<Section> a = this.findSectionByTutorialId(t.getId());
		for (final Section b : a)
			if (b.getTutorial().equals(section.getTutorial()))
				Assert.isTrue(section.getPosition() != b.getPosition(), "positionerror");

		return true;
	}

}

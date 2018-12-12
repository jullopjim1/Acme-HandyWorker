
package domain;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS

	private Map<String, String> name;

	@NotEmpty
	@ElementCollection(targetClass = String.class)
	public Map<String, String> getName() {
		return name;
	}

	public void setName(Map<String, String> name) {
		this.name = name;
	}

	// Relationships ---------------------------------------------------------
	private Collection<Category> subcategories;
	private Category rootcategory;

	@Valid
	@OneToMany
	public Collection<Category> getSubcategories() {
		return this.subcategories;
	}

	public void setSubcategories(final Collection<Category> subcategories) {
		this.subcategories = subcategories;
	}

	@Valid
	@ManyToOne(optional = true)
	public Category getRootcategory() {
		return this.rootcategory;
	}

	public void setRootcategory(final Category rootcategory) {
		this.rootcategory = rootcategory;
	}
}

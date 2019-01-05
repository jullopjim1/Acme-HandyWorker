
package domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS

	private Map<String, String>	name;

	@NotEmpty
	@ElementCollection
	public Map<String, String> getName() {
		return this.name;
	}

	public void setName(final Map<String, String> name) {
		this.name = name;
	}


	// Relationships ---------------------------------------------------------
	private Category				rootcategory;


	@Valid
	@ManyToOne(optional = true)
	public Category getRootcategory() {
		return this.rootcategory;
	}

	public void setRootcategory(final Category rootcategory) {
		this.rootcategory = rootcategory;
	}
}

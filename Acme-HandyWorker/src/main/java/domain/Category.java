package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String name;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	// Relationships ---------------------------------------------------------
	// TODO
	private Collection<Category> subcategories;
	private Category rootcategory;

	@NotNull
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

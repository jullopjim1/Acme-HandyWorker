package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String name;
	private boolean isRoot;
	
	@NotBlank
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull
	public boolean isRoot() {
		return isRoot;
	}
	
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	// Relationships ---------------------------------------------------------
	// TODO
}

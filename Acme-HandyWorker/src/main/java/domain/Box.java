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
public class Box extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String name;
	private boolean isSystem;

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public boolean getIsSystem() {
		return isSystem;
	}

	public void setIsSystem(boolean isSystem) {
		this.isSystem = isSystem;
	}

	// Relationships ---------------------------------------------------------
	// TODO
	private Collection<Box> subboxes;
	private Box rootbox;
	private Actor actor;

	@NotNull
	@Valid
	@OneToMany
	public Collection<Box> getSubboxes() {
		return this.subboxes;
	}

	public void setSubboxes(final Collection<Box> subboxes) {
		this.subboxes = subboxes;
	}

	@Valid
	@ManyToOne(optional = true)
	public Box getRootbox() {
		return this.rootbox;
	}

	public void setRootbox(final Box rootbox) {
		this.rootbox = rootbox;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}
}

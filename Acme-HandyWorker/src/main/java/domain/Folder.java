
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Folder extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String	name;
	private boolean	isSystem;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public boolean isSystem() {
		return this.isSystem;
	}

	public void setSystem(final boolean system) {
		this.isSystem = system;
	}


	// Relationships ---------------------------------------------------------
	// TODO
	private Collection<Message>	messages;
	private Collection<Folder>	subfolders;
	private Folder				rootfolder;
	private Actor				actor;


	@NotNull
	@Valid
	@ManyToMany(mappedBy = "folders")
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@NotNull
	@Valid
	@OneToMany(mappedBy = "rootfolder")
	public Collection<Folder> getSubfolders() {
		return this.subfolders;
	}

	public void setSubfolders(final Collection<Folder> subfolders) {
		this.subfolders = subfolders;
	}

	@Valid
	@ManyToOne(optional = true)
	public Folder getRootfolder() {
		return this.rootfolder;
	}

	public void setRootfolder(final Folder rootfolder) {
		this.rootfolder = rootfolder;
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

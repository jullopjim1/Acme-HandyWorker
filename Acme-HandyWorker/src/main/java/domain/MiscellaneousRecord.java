package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousRecord extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String title;
	private String link;
	private Collection<String> comments;

	
	@NotBlank
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@URL
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@ElementCollection
	public Collection<String> getComments() {
		return comments;
	}

	public void setComments(Collection<String> comments) {
		this.comments = comments;
	}

	// Relationships ---------------------------------------------------------
	// TODO
}

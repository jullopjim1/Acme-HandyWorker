
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Sponsorship extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String		banner;
	private String		link;
	private CreditCard	creditcard;


	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}

	@Valid
	@NotNull
	public CreditCard getCreditcard() {
		return this.creditcard;
	}

	public void setCreditcard(final CreditCard creditcard) {
		this.creditcard = creditcard;
	}


	// Relationships ---------------------------------------------------------
	// TODO
	private Collection<Tutorial>	tutorials;


	public Collection<Tutorial> getTutorials() {
		return this.tutorials;
	}

	public void setTutorials(final Collection<Tutorial> tutorials) {
		this.tutorials = tutorials;
	}
}


package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Endorser {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String make;


	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}


	// Relationships ---------------------------------------------------------
	private Finder finder;


	@Valid
	@OneToOne(cascade = CascadeType.ALL, optional = true)
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}

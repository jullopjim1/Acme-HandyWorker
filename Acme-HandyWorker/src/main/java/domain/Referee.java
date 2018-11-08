
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Referee extends Actor {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS

	// Relationships ---------------------------------------------------------
	private Collection<Report> reports;


	@NotNull
	@Valid
	@OneToMany
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

}

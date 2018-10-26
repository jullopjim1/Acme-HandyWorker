
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS

	// Relationships ---------------------------------------------------------
	// TODO

	private Collection<Endorser>	myEndorsees;
	private Collection<Endorser>	myEndorsers;


	@NotEmpty
	@OneToMany()
	public Collection<Endorser> getmyEndorsees() {
		return this.myEndorsees;
	}

	public void setMyEndorsers(final Collection<Endorser> endorsers) {
		this.myEndorsees = endorsers;
	}

	@NotEmpty
	@OneToMany()
	public Collection<Endorser> getmyEndorsers() {
		return this.myEndorsers;
	}

	public void setmyEndorsers(final Collection<Endorser> endorsers) {
		this.myEndorsers = endorsers;
	}

}

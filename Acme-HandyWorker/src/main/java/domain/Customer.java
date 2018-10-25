
package domain;

import java.util.Collection;

import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

public class Customer extends Actor {

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

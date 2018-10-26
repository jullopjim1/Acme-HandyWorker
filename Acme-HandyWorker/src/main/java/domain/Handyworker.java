package domain;

import java.util.Collection;

import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

public class HandyWorker extends Actor {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String make;
	
	@NotBlank
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}
	
	// Relationships ---------------------------------------------------------
	// TODO
	
	private Collection<Endorser> myEndorsees;
	private Collection<Endorser> myEndorsers;

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

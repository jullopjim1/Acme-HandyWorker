
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class HandyWorker extends Actor {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String	make;


	@NotBlank
	public String getMake() {
		return this.make;
	}

	public void setMake(final String make) {
		this.make = make;
	}


	// Relationships ---------------------------------------------------------
	// TODO

	private Collection<Endorser>	myEndorsees;
	private Collection<Endorser>	myEndorsers;
	private Finder					finder;
	private Curriculum				curriculum;
	private Collection<Tutorial>	tutorial;


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

	@OneToOne
	public Finder getFinder() {
		return this.finder;
	}

	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}

	public Collection<Tutorial> getTutorial() {
		return this.tutorial;
	}

	public void setTutorial(final Collection<Tutorial> tutorial) {
		this.tutorial = tutorial;
	}

}

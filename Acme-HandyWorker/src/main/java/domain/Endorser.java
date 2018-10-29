
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
public class Endorser extends Actor {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private double	score;


	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}


	// Relationships ---------------------------------------------------------

	private Collection<Endorsement>	writeEndorsements;
	private Collection<Endorsement>	receivedEndorsements;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "endorser")
	public Collection<Endorsement> getWriteEndorsements() {
		return this.writeEndorsements;
	}

	public void setWriteEndorsements(final Collection<Endorsement> writeEndorsements) {
		this.writeEndorsements = writeEndorsements;
	}
	@NotNull
	@Valid
	@OneToMany(mappedBy = "endorsee")
	public Collection<Endorsement> getReceivedEndorsements() {
		return this.receivedEndorsements;
	}

	public void setReceivedEndorsements(final Collection<Endorsement> receivedEndorsements) {
		this.receivedEndorsements = receivedEndorsements;
	}

}

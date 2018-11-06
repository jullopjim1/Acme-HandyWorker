
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Endorser extends Actor {

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

}

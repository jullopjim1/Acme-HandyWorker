
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

@Entity
@Access(AccessType.PROPERTY)
public class Endorser extends DomainEntity {

	// Identification ---------------------------------------------------------
	//ATRIBUTOS
	private String		comment;
	private DateTime	moment;


	@NotBlank
	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	@Past
	public DateTime getMoment() {
		return this.moment;
	}

	public void setMoment(final DateTime moment) {
		this.moment = moment;
	}

}

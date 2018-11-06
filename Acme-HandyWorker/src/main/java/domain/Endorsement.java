
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Endorsement extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private Date	moment;
	private String	comments;
	private double	score;


	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}


	// Relationships ---------------------------------------------------------

	private Endorser	endorser;
	private Endorser	endorsee;


	@ManyToOne(optional = false)
	@Valid
	public Endorser getEndorser() {
		return this.endorser;
	}

	public void setEndorser(final Endorser endorser) {
		this.endorser = endorser;
	}

	@ManyToOne(optional = false)
	@Valid
	public Endorser getEndorsee() {
		return this.endorsee;
	}

	public void setEndorsee(final Endorser endorsee) {
		this.endorsee = endorsee;
	}

}

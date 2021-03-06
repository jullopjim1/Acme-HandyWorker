
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Note extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private Date	moment;
	private String	mandatoryCommentReferee;
	private String	mandatoryCommentCustomer;
	private String	mandatoryCommentHandyWorker;
	private Boolean	isFinal;


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

	public String getMandatoryCommentReferee() {
		return this.mandatoryCommentReferee;
	}

	public void setMandatoryCommentReferee(final String mandatoryCommentReferee) {
		this.mandatoryCommentReferee = mandatoryCommentReferee;
	}

	public String getMandatoryCommentCustomer() {
		return this.mandatoryCommentCustomer;
	}

	public void setMandatoryCommentCustomer(final String mandatoryCommentCustomer) {
		this.mandatoryCommentCustomer = mandatoryCommentCustomer;
	}

	public String getMandatoryCommentHandyWorker() {
		return this.mandatoryCommentHandyWorker;
	}

	public void setMandatoryCommentHandyWorker(final String mandatoryCommentHandyWorker) {
		this.mandatoryCommentHandyWorker = mandatoryCommentHandyWorker;
	}

	@Valid
	public Boolean getIsFinal() {
		return this.isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}


	// Relationships ---------------------------------------------------------
	private Report	report;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Report getReport() {
		return this.report;
	}

	public void setReport(final Report report) {
		this.report = report;
	}
}

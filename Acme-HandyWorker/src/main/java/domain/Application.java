
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private Date				moment;
	private String				status;
	private double				price;
	private Collection<String>	customerComments;
	private Collection<String>	handyWorkerComments;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Past
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(final double price) {
		this.price = price;
	}

	@ElementCollection
	public Collection<String> getCustomerComments() {
		return this.customerComments;
	}

	public void setCustomerComments(final Collection<String> customerComments) {
		this.customerComments = customerComments;
	}

	@ElementCollection
	public Collection<String> getHandyWorkerComments() {
		return this.handyWorkerComments;
	}

	public void setHandyWorkerComments(final Collection<String> handyWorkerComments) {
		this.handyWorkerComments = handyWorkerComments;
	}

	// Relationships ---------------------------------------------------------
	// TODO
}

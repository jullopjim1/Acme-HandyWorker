package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String ticker;

	@NotBlank
	@Column(unique = true)
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	// Relationships ---------------------------------------------------------
	// TODO
}

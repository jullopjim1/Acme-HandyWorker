
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Ticker extends DomainEntity {

	//Atribute-------------------------------------------------------------------

	private String ticker;


	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^\\d{6}-(\\d?\\w){6}$")
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

}

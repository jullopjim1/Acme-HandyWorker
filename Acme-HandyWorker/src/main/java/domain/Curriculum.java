
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	//Atributtes------------------------------------------------------------------------------

	// Relationships ---------------------------------------------------------

	private HandyWorker	handyWorker;
	private Ticker		ticker;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Ticker getTicker() {
		return this.ticker;
	}

	public void setTicker(final Ticker ticker) {
		this.ticker = ticker;
	}
}

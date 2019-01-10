package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import domain.Application;

@Access(AccessType.PROPERTY)
public class ApplicationColour {

	// Constructor ---------------------------------------------------
	public ApplicationColour() {
		super();
	}

	// Attributes -----------------------------------------------------
	private Application	application;
	private String		color;


	@Valid
	public Application getApplication() {
		return this.application;
	}

	public void setApplication(final Application application) {
		this.application = application;
	}

	@NotNull
	public String getColor() {
		return this.color;
	}

	public void setColor(final String color) {
		this.color = color;
	}
}

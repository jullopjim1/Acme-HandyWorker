package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.constraints.NotNull;

@Access(AccessType.PROPERTY)
public class ApplicationForm {

	// Constructor ---------------------------------------------------
	public ApplicationForm() {
		super();
	}

	// Attributes -----------------------------------------------------
	private int id;
	private String comments;

	@NotNull
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotNull
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}

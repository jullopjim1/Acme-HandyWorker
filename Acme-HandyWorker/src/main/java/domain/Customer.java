
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorser {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS

	// Relationships ---------------------------------------------------------
	// TODO
	private Collection<FixUpTask>	fixUpTasks;
	private Collection<Complaint>	complaints;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}
	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}

}

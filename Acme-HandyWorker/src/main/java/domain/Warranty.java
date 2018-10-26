
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Access(AccessType.PROPERTY)
public class Warranty extends DomainEntity {

	// Identification ---------------------------------------------------------
	//ATRIBUTOS
	private String title;
	private String terms;
	private Collection<String> laws;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getTerms() {
		return terms;
	}
	
	public void setTerms(String terms) {
		this.terms = terms;
	}
	
	@NotEmpty
	@ElementCollection
	public Collection<String> getLaws() {
		return laws;
	}
	
	public void setLaws(Collection<String> laws) {
		this.laws = laws;
	}
	
	// Relationships ---------------------------------------------------------
	//TODO
}

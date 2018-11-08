
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private String	keyword;
	private String	namecategory;
	private Double	priceMin;
	private Double	priceMax;
	private Date	dateMin;
	private Date	dateMax;
	private String	namewarranty;
	private Date	lastUpdate;


	@NotBlank
	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(final String keyword) {
		this.keyword = keyword;
	}

	public String getNamecategory() {
		return this.namecategory;
	}

	public void setNamecategory(final String namecategory) {
		this.namecategory = namecategory;
	}

	public Double getPriceMin() {
		return this.priceMin;
	}

	public void setPriceMin(final Double priceMin) {
		this.priceMin = priceMin;
	}

	public Double getPriceMax() {
		return this.priceMax;
	}

	public void setPriceMax(final Double priceMax) {
		this.priceMax = priceMax;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDateMin() {
		return this.dateMin;
	}

	public void setDateMin(final Date dateMin) {
		this.dateMin = dateMin;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	public Date getDateMax() {
		return this.dateMax;
	}

	public void setDateMax(final Date dateMax) {
		this.dateMax = dateMax;
	}

	public String getNamewarranty() {
		return this.namewarranty;
	}

	public void setNamewarranty(final String namewarranty) {
		this.namewarranty = namewarranty;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Past
	public Date getLastUpdate() {
		return this.lastUpdate;
	}

	public void setLastUpdate(final Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}


	// Relationships ---------------------------------------------------------
	private Collection<FixUpTask> fixUpTasks;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}
}

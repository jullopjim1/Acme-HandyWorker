package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Category;

@Access(AccessType.PROPERTY)
public class CategoryForm {

	// Constructor ---------------------------------------------------
	public CategoryForm() {
		super();
	}

	// Attributes -----------------------------------------------------
	private int id;
	private String nameEN;
	private String nameES;
	private Category rootcategory;

	@NotNull
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotBlank
	public String getNameEN() {
		return nameEN;
	}

	public void setNameEN(String nameEN) {
		this.nameEN = nameEN;
	}

	@NotBlank
	public String getNameES() {
		return nameES;
	}

	public void setNameES(String nameES) {
		this.nameES = nameES;
	}

	@Valid
	public Category getRootcategory() {
		return rootcategory;
	}

	public void setRootcategory(Category rootcategory) {
		this.rootcategory = rootcategory;
	}
}

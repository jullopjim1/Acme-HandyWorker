
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private int					varTax;
	private int					countryCode;
	private String				welcomeMessageES;
	private String				welcomeMessageEN;
	private String				banner;
	private Collection<String>	spamWords;
	private int					finderCacheTime;
	private int					finderMaxResults;
	private Collection<String>	negativeWords;
	private Collection<String>	positiveWords;


	public void setFinderMaxResults(final Integer finderMaxResults) {
		this.finderMaxResults = finderMaxResults;
	}

	@Range(min = 0, max = 100)
	public int getVarTax() {
		return this.varTax;
	}

	public void setVarTax(final int varTax) {
		this.varTax = varTax;
	}

	@Range(min = 0, max = 999)
	public int getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(final int countryCode) {
		this.countryCode = countryCode;
	}

	@NotBlank
	public String getWelcomeMessageES() {
		return this.welcomeMessageES;
	}

	public void setWelcomeMessageES(final String welcomeMessage) {
		this.welcomeMessageES = welcomeMessage;
	}

	@NotBlank
	public String getWelcomeMessageEN() {
		return this.welcomeMessageEN;
	}

	public void setWelcomeMessageEN(final String welcomeMessage) {
		this.welcomeMessageEN = welcomeMessage;
	}

	@NotBlank
	@URL
	public String getBanner() {
		return this.banner;
	}

	public void setBanner(final String banner) {
		this.banner = banner;
	}

	@Range(min = 1, max = 24)
	public int getFinderCacheTime() {
		return this.finderCacheTime;
	}

	public void setFinderCacheTime(final int finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
	}

	@Range(min = 0, max = 100)
	public int getFinderMaxResults() {
		return this.finderMaxResults;
	}
	public void setFinderMaxResults(final int finderMaxResults) {
		this.finderMaxResults = finderMaxResults;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getSpamWords() {
		return this.spamWords;
	}

	public void setSpamWords(final Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getNegativeWords() {
		return this.negativeWords;
	}

	public void setNegativeWords(final Collection<String> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@NotEmpty
	@ElementCollection
	public Collection<String> getPositiveWords() {
		return this.positiveWords;
	}

	public void setPositiveWords(final Collection<String> positiveWords) {
		this.positiveWords = positiveWords;
	}

	// Relationships ---------------------------------------------------------
	// TODO
}

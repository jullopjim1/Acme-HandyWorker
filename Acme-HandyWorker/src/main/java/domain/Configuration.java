package domain;

import java.util.Collection;
import java.util.Map;

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
	private int varTax;
	private int countryCode;
	private Map<String, String> welcomeMessage;
	private String banner;
	private Map<String, Collection<String>> spamWords;
	private int finderCacheTime;
	private int finderMaxResults;
	private Map<String, Collection<String>> negativeWords;
	private Map<String, Collection<String>> positiveWords;

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
	@ElementCollection(targetClass = String.class)
	public Map<String, String> getWelcomeMessage() {
		return welcomeMessage;
	}

	public void setWelcomeMessage(Map<String, String> welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
	}

	@NotEmpty
	@ElementCollection(targetClass = org.hibernate.mapping.Collection.class)
	public Map<String, Collection<String>> getNegativeWords() {
		return negativeWords;
	}

	public void setNegativeWords(Map<String, Collection<String>> negativeWords) {
		this.negativeWords = negativeWords;
	}

	@NotEmpty
	@ElementCollection(targetClass = org.hibernate.mapping.Collection.class)
	public Map<String, Collection<String>> getPositiveWords() {
		return positiveWords;
	}

	public void setPositiveWords(Map<String, Collection<String>> positiveWords) {
		this.positiveWords = positiveWords;
	}

	@NotEmpty
	@ElementCollection(targetClass = org.hibernate.mapping.Collection.class)
	public Map<String, Collection<String>> getSpamWords() {
		return spamWords;
	}

	public void setSpamWords(Map<String, Collection<String>> spamWords) {
		this.spamWords = spamWords;
	}

	// Relationships ---------------------------------------------------------
}

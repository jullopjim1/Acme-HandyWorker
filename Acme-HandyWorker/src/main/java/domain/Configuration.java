package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Identification ---------------------------------------------------------
	// ATRIBUTOS
	private Collection<String> spamWords;
	private Integer finderCacheTime;
	private Integer finderMaxResults;

	@ElementCollection
	public Collection<String> getSpamWords() {
		return spamWords;
	}

	public void setSpamWords(Collection<String> spamWords) {
		this.spamWords = spamWords;
	}

	@NotNull
	@Range(min = 1, max = 1440)
	public Integer getFinderCacheTime() {
		return finderCacheTime;
	}

	public void setFinderCacheTime(Integer finderCacheTime) {
		this.finderCacheTime = finderCacheTime;
	}

	@NotNull
	@Range(min = 1, max = 100)
	public Integer getFinderMaxResults() {
		return finderMaxResults;
	}

	public void setFinderMaxResults(Integer finderMaxResults) {
		this.finderMaxResults = finderMaxResults;
	}

	// Relationships ---------------------------------------------------------
	// TODO
}

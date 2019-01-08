
package forms;

import java.util.Collection;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;

import domain.DomainEntity;

@Access(AccessType.PROPERTY)
public class ConfigurationForm extends DomainEntity {

	private int								varTax;
	private int								countryCode;
	private String							banner;
	private int								finderCacheTime;
	private int								finderMaxResults;
	private Map<String, String>				welcomeMessageES;
	private Map<String, String>				welcomeMessageEN;
	private Map<String, Collection<String>>	spamWordsES;
	private Map<String, Collection<String>>	spamWordEN;
	private Map<String, Collection<String>>	negativeWordsES;
	private Map<String, Collection<String>>	negativeWordsEN;
	private Map<String, Collection<String>>	positiveWordsES;
	private Map<String, Collection<String>>	positiveWordsEN;

}

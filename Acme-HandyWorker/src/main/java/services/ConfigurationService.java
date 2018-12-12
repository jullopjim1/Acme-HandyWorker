
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Repository-------------------------------------------------------------------------

	@Autowired
	private ConfigurationRepository configurationRepository;

	// Services---------------------------------------------------------------------------

	// Constructor------------------------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	// Simple
	// CRUD------------------------------------------------------------------------

	public Configuration create() {
		final Configuration configuration = new Configuration();

		final Map<String, Collection<String>> spamWords = new HashMap<>();
		final Map<String, Collection<String>> negativeWords = new HashMap<>();
		final Map<String, Collection<String>> positiveWords = new HashMap<>();
		Map<String, String> welcomeMessage = new HashMap<>();

		configuration.setVarTax(21);
		configuration.setCountryCode(34);
		configuration.setWelcomeMessage(welcomeMessage);
		configuration.setBanner("");
		configuration.setSpamWords(spamWords);
		configuration.setFinderCacheTime(1);
		configuration.setFinderMaxResults(100);
		configuration.setNegativeWords(negativeWords);
		configuration.setPositiveWords(positiveWords);

		return configuration;

	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> configurations;

		configurations = this.configurationRepository.findAll();
		Assert.notNull(configurations);

		return configurations;
	}

	public Configuration findOne() {
		return new ArrayList<>(findAll()).get(0);
	}

	public Configuration save(final Configuration configuration) {
		final Configuration saved = this.configurationRepository.save(configuration);
		return saved;
	}

	public void delete(final Configuration entity) {
		this.configurationRepository.delete(entity);
	}

	// Other
	// Methods---------------------------------------------------------------------------

	public Collection<String> internacionalizcionListas(Map<String, Collection<String>> words) {

		String laungage = LocaleContextHolder.getLocale().getLanguage();
		Collection<String> res;

		if (laungage.equals("en")) {
			res = words.get(laungage);
		} else {
			res = words.get(laungage);
		}

		return res;
	}

	public String internacionalizcion(Map<String, String> words) {

		String laungage = LocaleContextHolder.getLocale().getLanguage();
		String res;

		if (laungage.equals("en")) {
			res = words.get(laungage);
		} else {
			res = words.get(laungage);
		}

		return res;
	}
}

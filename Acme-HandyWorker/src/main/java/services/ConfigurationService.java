
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;


	//Services---------------------------------------------------------------------------

	//Constructor------------------------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------

	public Configuration create() {
		final Configuration configuration = new Configuration();

		final Collection<String> spamWords = new ArrayList<>();
		final Collection<String> negativeWords = new ArrayList<>();
		final Collection<String> positiveWords = new ArrayList<>();

		configuration.setVarTax(21);
		configuration.setCountryCode(34);
		configuration.setWelcomeMessageEN("");
		configuration.setWelcomeMessageES("");
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

	public Configuration findOne(final Integer configurationId) {
		return this.configurationRepository.findOne(configurationId);
	}
	public Configuration save(final Configuration configuration) {
		final Configuration saved = this.configurationRepository.save(configuration);
		return saved;
	}

	public void delete(final Configuration entity) {
		this.configurationRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

}

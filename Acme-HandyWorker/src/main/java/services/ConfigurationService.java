
package services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

		return configuration;

	}

	public List<Configuration> findAll() {
		return this.configurationRepository.findAll();
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

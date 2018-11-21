
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import repositories.ProfileRepository;
import domain.Profile;

@Service
@Transactional
public class ProfileService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ProfileRepository	ProfileRepository;
	private ActorRepository		actorRepository;


	//Constructor----------------------------------------------------------------------------

	public ProfileService() {
		super();
	}

	// Simple CRUD methods -------------------------------------------------------------------
	public Profile create(final int ActorId) {
		final Profile profile = new Profile();
		profile.setActor(this.actorRepository.findOne(ActorId));
		return profile;
	}

	public Collection<Profile> findAll() {
		Collection<Profile> profiles;

		profiles = this.ProfileRepository.findAll();
		Assert.notNull(profiles);

		return profiles;
	}
	public Profile findOne(final int ProfileID) {
		Profile profile;
		profile = this.ProfileRepository.findOne(ProfileID);
		Assert.notNull(ProfileID);

		return profile;
	}

	public Profile save(final Profile profile) {
		Assert.notNull(profile);
		Profile result;

		result = this.ProfileRepository.save(profile);

		return result;
	}
	public void delete(final Profile Profile) {

		Assert.notNull(Profile);
		this.ProfileRepository.delete(Profile);
	}

}

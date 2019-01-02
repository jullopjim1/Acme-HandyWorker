
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import repositories.ActorRepository;
import security.UserAccount;

@Service
@Transactional
public class ActorService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ActorRepository actorRepository;


	public Actor findOne(final int ActorId) {
		return this.actorRepository.findOne(ActorId);
	}

	Actor findActorByUsername(final String username) {
		final Actor actor = this.actorRepository.findActorByUsername(username);
		return actor;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}

	public Collection<Actor> findAll() {
		final Collection<Actor> actors = this.actorRepository.findAll();
		Assert.notNull(actors);

		return actors;
	}

}


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

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		final Actor saved = this.actorRepository.save(actor);
		return saved;
	}

	//Other Methods----------------------------------------------------------

	public void ban(final Actor actor) {
		actor.setIsBanned(true);
		final UserAccount account = actor.getUserAccount();

		this.save(actor);
	}

	public void unban(final Actor actor) {
		actor.setIsBanned(false);
		actor.setIsSuspicious(false);
		final UserAccount account = actor.getUserAccount();

		this.save(actor);
	}

	public Collection<Actor> findSuspiciuosActors() {
		return this.actorRepository.findSuspiciuosActors();
	}

}

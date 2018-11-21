
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import domain.Actor;

@Service
public class ActorService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ActorRepository	actor;


	public Actor findOne(final int ActorId) {
		return this.actor.findOne(ActorId);
	}

}

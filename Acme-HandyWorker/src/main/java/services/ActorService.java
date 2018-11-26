
package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.ActorRepository;
import domain.Actor;

@Service
public class ActorService {

	// Managed repository ----------------------------------------------------------------
	@Autowired
	private ActorRepository	actorRepository;


	public Actor findOne(final int ActorId) {
		return this.actorRepository.findOne(ActorId);
	}

	
	Actor findActorByUsername(String username){
		Actor actor = actorRepository.findActorByUsername(username);
		return actor;
	}
	
	public Actor findByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findByUserAccount(userAccount.getId());
	}
}

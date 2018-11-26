
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;

@Service
@Transactional
public class BoxService {

	//Repository-------------------------------------------------------------------------

	@Autowired
	private BoxRepository	boxRepository;

	//Services---------------------------------------------------------------------------
	@Autowired
	private ActorService	actorService;


	//Constructor------------------------------------------------------------------------

	public BoxService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------
	public Box create() {
		final Box box = new Box();

		final String name = "";
		final boolean isSystem = false;
		final Collection<Box> subboxes = new ArrayList<>();
		final Box rootbox = null;
		// TODO falta create actor
		final Actor actor = new Actor();
		//actor = this.actorService.create();

		box.setName(name);
		box.setIsSystem(isSystem);
		box.setSubboxes(subboxes);
		box.setRootbox(rootbox);
		box.setActor(actor);

		return box;

	}

	public List<Box> findAll() {
		return this.boxRepository.findAll();
	}

	public Box findOne(final Integer boxId) {
		return this.boxRepository.findOne(boxId);
	}
	public Box save(final Box box) {

		final Box saved = this.boxRepository.save(box);

		return saved;
	}

	public void delete(final Box entity) {
		this.boxRepository.delete(entity);
	}

	//Other Methods---------------------------------------------------------------------------

	/*
	 * Crea y guarda las system box pasando por parametro el actor
	 */
	public void addSystemBox(final Actor actor) {
		// Se inician las boxes
		final Collection systemBoxes = this.createSystemBox(actor);

		// Se guarda en la base de datos, despues de guardar el actor
		this.boxRepository.save(systemBoxes);

	}

	/*
	 * Método para crear las system box
	 */
	private Collection<Box> createSystemBox(final Actor actor) {
		final Collection<Box> result = new ArrayList<>();

		final Collection<Box> subboxes = new ArrayList<>();
		//TODO ¿Que es rootBox?
		final Box rootbox = null;

		// Iniciar trash box
		final Box trash = new Box();
		trash.setName("trash box");
		trash.setIsSystem(true);
		trash.setSubboxes(subboxes);
		trash.setRootbox(rootbox);
		trash.setActor(actor);

		// Iniciar in box
		final Box inBox = new Box();
		inBox.setName("in box");
		inBox.setIsSystem(true);
		inBox.setSubboxes(subboxes);
		inBox.setRootbox(rootbox);
		inBox.setActor(actor);

		// Iniciar out box
		final Box outBox = new Box();
		outBox.setName("out box");
		outBox.setIsSystem(true);
		outBox.setSubboxes(subboxes);
		outBox.setRootbox(rootbox);
		outBox.setActor(actor);

		// Iniciar spam box
		final Box spamBox = new Box();
		spamBox.setName("spam box");
		spamBox.setIsSystem(true);
		spamBox.setSubboxes(subboxes);
		spamBox.setRootbox(rootbox);
		spamBox.setActor(actor);

		//Se añade todas las boxes a la collection
		result.add(spamBox);
		result.add(outBox);
		result.add(inBox);
		result.add(trash);

		return result;

	}

}

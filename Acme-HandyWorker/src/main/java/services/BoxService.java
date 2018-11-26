
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import security.LoginService;
import security.UserAccount;
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

	@Autowired
	private MessageService	messageService;


	//Constructor------------------------------------------------------------------------

	public BoxService() {
		super();
	}

	//Simple CRUD------------------------------------------------------------------------
	public Box create() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount, "Debe estar logeado en el sistema para crear una carpeta");
		final Actor actor = this.actorService.findByUserAccount(userAccount);

		final Box box = new Box();

		final String name = "";
		final boolean isSystem = false;
		final Collection<Box> subboxes = new ArrayList<>();
		final Box rootbox = null;

		box.setName(name);
		box.setIsSystem(isSystem);
		box.setSubboxes(subboxes);
		box.setRootbox(rootbox);
		box.setActor(actor);

		return box;

	}

	public Collection<Box> findAll() {
		Collection<Box> boxes;

		boxes = this.boxRepository.findAll();
		Assert.notNull(boxes);

		return boxes;
	}

	public Box findOne(final Integer boxId) {
		return this.boxRepository.findOne(boxId);
	}
	public Box save(final Box box) {
		final Collection<String> systemBox = new ArrayList<>();
		systemBox.add("trash box");
		systemBox.add("in box");
		systemBox.add("out box");
		systemBox.add("spam box");

		Box oldRootBox;

		final Box newRootBox = box.getRootbox();

		Assert.isTrue(!box.getIsSystem(), "No se puede modificar una carpeta del sistema");
		if (box.getId() == 0)
			Assert.isTrue(!systemBox.contains(box.getName()), "No se puede crear carpetas con nombres reservados");
		else {
			oldRootBox = this.findOne(box.getId()).getRootbox();
			if (!newRootBox.equals(oldRootBox)) {
				oldRootBox.getSubboxes().remove(box);
				this.boxRepository.save(oldRootBox);
			}
		}

		final Box saved = this.boxRepository.save(box);

		if (box.getId() == 0)
			if (newRootBox != null) {
				newRootBox.getSubboxes().add(saved);
				this.boxRepository.save(newRootBox);
			}

		return saved;
	}
	public void delete(final Box entity) {
		// TODO DELETE BOX ¿Si se borra un actor se borra sus carpetas?
		Assert.isTrue(!entity.getIsSystem(), "No se puede eliminar una carpeta del sistema");

		final Box rootBox = entity.getRootbox();
		if (rootBox != null) {
			rootBox.getSubboxes().remove(entity);
			this.boxRepository.save(rootBox);
		}

		this.messageService.deleteByBox(entity);

		final Collection<Box> subBoxes = entity.getSubboxes();
		this.delete(subBoxes);

		this.boxRepository.delete(entity);
	}

	private void delete(final Collection<Box> subBoxes) {
		if (subBoxes.size() > 0)
			for (final Box box : subBoxes) {
				this.messageService.deleteByBox(box);
				this.delete(box.getSubboxes());
				final Box rootBox = box.getRootbox();

				rootBox.getSubboxes().remove(box);
				this.boxRepository.save(rootBox);

				this.boxRepository.delete(box);
			}

	}
	//Other Methods---------------------------------------------------------------------------

	/*
	 * Crea y guarda las system box pasando por parametro el actor
	 */
	public void addSystemBox(final Actor actor) {
		// Se inician las boxes
		final Collection<Box> systemBoxes = this.createSystemBox(actor);

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

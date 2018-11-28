package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class CustomerService {

	// Repository-----------------------------------------------

	@Autowired
	private CustomerRepository customerRepository;

	// Services-------------------------------------------------
	@Autowired
	private ActorService actorService;

	// Constructor----------------------------------------------

	public CustomerService() {

		super();
	}

	// Simple CRUD----------------------------------------------

	public Customer create() {
		final Customer customer = new Customer();
		final UserAccount userAccount = new UserAccount();
		final Collection<Authority> authorities = new ArrayList<Authority>();

		final Authority a = new Authority();
		a.setAuthority("CUSTOMER");
		authorities.add(a);
		userAccount.setAuthorities(authorities);
		final Collection<FixUpTask> fixUpTasks = new ArrayList<FixUpTask>();
		customer.setFixUpTasks(fixUpTasks);
		customer.setIsBanned(false);
		customer.setIsSuspicious(false);
		return customer;
	}

	public List<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final Integer customerId) {
		return this.customerRepository.findOne(customerId);
	}

	public Customer save(final Customer customer) {
		Assert.notNull(customer);

		//SI NO ES USUARIO NUEVO
		if (customer.getId() != 0) {
			// COJO ACTOR ACTUAL
			Actor actorActual = actorService.findActorByUsername(LoginService
					.getPrincipal().getUsername());
			Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

			// COMPRUEBO RESTRICCIONES DE USUARIOS
			if (!(actorActual.getId() == customer.getId())) {
				Assert.notNull(null,
						"UN CUSTOMER SOLO PUEDE EDITAR SUS PROPIOS DATOS PERSONALES");
			}
		}
		
		//GUARDO CUSTOMER
		final Customer saved = this.customerRepository.save(customer);
		return saved;
	}

	public void delete(final Customer customer) {
		Assert.notNull(customer, "CUSTOMER A BORRAR NO PUEDE SER NULL");

		// COJO ACTOR ACTUAL
		Actor actorActual = actorService.findActorByUsername(LoginService
				.getPrincipal().getUsername());
		Assert.notNull(actorActual, "NO HAY ACTOR DETECTADO");

		// COMPRUEBO RESTRICCIONES DE USUARIOS
		if (!actorActual.getUserAccount().getAuthorities().toString()
				.contains("ADMIN")) {
			Assert.notNull(null, "SOLO ADMIN PUEDE BORRAR CUSTOMER");
		}

		// BORRO EL CUSTOMER
		this.customerRepository.delete(customer);
	}

	// Other Methods--------------------------------------------

}
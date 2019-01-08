
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select h from Customer h where h.userAccount.id = ?1")
	public Customer findCustomerByUserAccount(int userAccountId);

	@Query("select c from Customer c where c.userAccount.username=?1")
	Customer findCustomerByUsername(String username);

}

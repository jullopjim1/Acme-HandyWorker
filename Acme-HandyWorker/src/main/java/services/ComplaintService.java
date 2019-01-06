
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Ticker;

@Service
@Transactional
public class ComplaintService {

	// Repository-----------------------------------------------
	@Autowired
	private ComplaintRepository	complaintRepository;

	// Services-------------------------------------------------

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private TickerService		tickerService;


	// Constructor----------------------------------------------
	public ComplaintService() {
		super();
	}
	// Simple CRUD----------------------------------------------

	public Complaint create(final int customerId, final int fixUpTaskId) {
		final Complaint complaint = new Complaint();
		final Customer customer = this.customerService.findOne(customerId);
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		//Ticker Unico
		final Ticker ticker = this.tickerService.isUniqueTicker();
		final Ticker saved = this.tickerService.save(ticker);

		complaint.setFixUpTask(fixUpTask);
		complaint.setCustomer(customer);
		complaint.setTicker(saved);
		complaint.setMoment(new Date(System.currentTimeMillis() - 1000));
		complaint.setIsFinal(false);
		return complaint;
	}

	public List<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	public Complaint findOne(final Integer complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		final Complaint saved = this.complaintRepository.save(complaint);

		return saved;
	}

	public void delete(final Complaint complaint) {
		this.complaintRepository.delete(complaint);

	}
	// Other Methods--------------------------------------------

	public Collection<Complaint> findComplaintsByCustomerId(final int customerId) {
		final Collection<Complaint> complaints = this.complaintRepository.findComplaintsByCustomerId(customerId);
		return complaints;
	}

	public Collection<Complaint> findComplaintsByRefereeId(final int refereeId) {
		final Collection<Complaint> complaints = this.complaintRepository.findComplaintsByRefereeId(refereeId);
		return complaints;
	}
	public ArrayList<Customer> queryB4() {
		return this.complaintRepository.queryB4();
	}

}

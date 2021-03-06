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
import security.LoginService;
import domain.Complaint;
import domain.Customer;
import domain.FixUpTask;
import domain.Report;
import domain.Ticker;

@Service
@Transactional
public class ComplaintService {

	// Repository-----------------------------------------------
	@Autowired
	private ComplaintRepository complaintRepository;

	// Services-------------------------------------------------

	@Autowired
	private CustomerService customerService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private TickerService tickerService;

	@Autowired
	private ReportService reportService;

	// Constructor----------------------------------------------
	public ComplaintService() {
		super();
	}

	// Simple CRUD----------------------------------------------

	public Complaint create(final int fixUpTaskId) {
		final Complaint complaint = new Complaint();
		final Customer customer = this.customerService
				.findByUserAccount(LoginService.getPrincipal().getId());
		final FixUpTask fixUpTask = this.fixUpTaskService.findOne(fixUpTaskId);

		// Ticker Unico
		final Ticker ticker = this.tickerService.create();
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
		// BORRO SU REPORT
		Report report = reportService
				.findReportByComplaintId(complaint.getId());
		if (report != null) {
			reportService.delete(report);
		}
		
		this.complaintRepository.delete(complaint);

	}

	// Other Methods--------------------------------------------

	public Collection<Complaint> findComplaintsByCustomerId(final int customerId) {
		final Collection<Complaint> complaints = this.complaintRepository
				.findComplaintsByCustomerId(customerId);
		return complaints;
	}

	public Collection<Complaint> findComplaintsByRefereeId(final int refereeId) {
		final Collection<Complaint> complaints = this.complaintRepository
				.findComplaintsByRefereeId(refereeId);
		return complaints;
	}

	public ArrayList<Customer> queryB4() {
		return this.complaintRepository.queryB4();
	}

	public Complaint findComplaintByTaskId(final int fixUpTaskId) {
		return this.complaintRepository.findComplaintByTaskId(fixUpTaskId);
	}

	public Complaint findComplaintFinalByTaskId(final int fixUpTaskId) {
		return this.complaintRepository.findComplaintFinalByTaskId(fixUpTaskId);
	}

	public Collection<Complaint> findComplaintsByHandyWorkerId(final int handyId) {
		return this.complaintRepository.findComplaintsByHandyWorkerId(handyId);
	}

}


package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;

@Service
@Transactional
public class ComplaintService {

	//Repository-----------------------------------------------
	@Autowired
	private ComplaintRepository	complaintRepository;
	//Services-------------------------------------------------
	@Autowired
	private CustomerService		customerService;

	@Autowired
	private ComplaintService	ComplaintService;


	//Constructor----------------------------------------------
	public ComplaintService() {
		super();
	}
	//Simple CRUD----------------------------------------------

	public Complaint create() {
		final Complaint complaint = new Complaint();

		complaint.setTicker("");
		complaint.setMoment(new Date(System.currentTimeMillis() - 1000));
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

	public void delete(final Complaint entity) {
		this.complaintRepository.delete(entity);

	}
	//Other Methods--------------------------------------------

}


package services;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Repository-----------------------------------------------
	@Autowired
	private ReportRepository	reportRepository;
	// Services-------------------------------------------------
	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	// Constructor----------------------------------------------
	public ReportService() {
		super();

	}

	// Simple CRUD----------------------------------------------

	public Report create(final int complaintId, final int refereeId) {
		final Report report = new Report();
		final Complaint complaint = this.complaintService.findOne(complaintId);
		final Referee referee = this.refereeService.findOne(refereeId);

		report.setComplaint(complaint);
		report.setReferee(referee);
		report.setMoment(new Date(System.currentTimeMillis() - 1000));
		report.setIsFinal(false);

		return report;
	}

	public List<Report> findAll() {
		return this.reportRepository.findAll();
	}

	public Report findOne(final Integer reportId) {
		return this.reportRepository.findOne(reportId);
	}

	public Report save(final Report report) {
		Assert.notNull(report);
		report.setMoment(new Date(System.currentTimeMillis() - 1000));
		final Report saved = this.reportRepository.save(report);
		return saved;
	}

	public void delete(final Report report) {
		// TODO METODO PARA BORRAR LAS NOTE (A TESTEAR)
		// if (!report.getNote().isEmpty()) {
		// for (Note n : report.getNote()) {
		// noteService.delete(n);
		// }
		// }

		this.reportRepository.delete(report);
	}
	// Other Methods--------------------------------------------
	public Report findReportByComplaintId(final int complaintId) {
		return this.reportRepository.findReportByComplaintId(complaintId);
	}

	public Report findReportFinal(final int complaintId) {
		return this.reportRepository.findReportFinal(complaintId);

	}
	public Double queryB2AVG() {
		return this.reportRepository.queryB2AVG();
	}

	public Double queryB2MAX() {
		return this.reportRepository.queryB2MAX();
	}

	public Double queryB2MIN() {
		return this.reportRepository.queryB2MIN();
	}

	public Double queryB2STDDEV() {
		return this.reportRepository.queryB2STDDEV();
	}

}

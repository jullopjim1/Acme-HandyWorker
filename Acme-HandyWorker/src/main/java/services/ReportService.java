
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class ReportService {

	//Repository-----------------------------------------------
	@Autowired
	private ReportRepository	reportRepository;
	//Services-------------------------------------------------
	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private RefereeService		refereeService;


	//Constructor----------------------------------------------
	public ReportService() {
		super();
	}
	//Simple CRUD----------------------------------------------

	public Report create() {
		final Report report = new Report();

		final Collection<Note> notes = new ArrayList<>();

		report.setIsFinal(false);
		report.setNote(notes);

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
		final Report saved = this.reportRepository.save(report);
		return saved;
	}

	public void delete(final Report entity) {
		this.reportRepository.delete(entity);

	}
	//Other Methods--------------------------------------------

}

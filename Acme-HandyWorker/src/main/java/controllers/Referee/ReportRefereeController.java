
package controllers.Referee;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ComplaintService;
import services.NoteService;
import services.RefereeService;
import services.ReportService;
import controllers.AbstractController;
import domain.Actor;
import domain.Complaint;
import domain.Referee;
import domain.Report;

@Controller
@RequestMapping("/report/referee")
public class ReportRefereeController extends AbstractController {

	//Service---------------------------------------------------------

	@Autowired
	private ReportService		reportService;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private NoteService			noteService;

	@Autowired
	private ComplaintService	complaintService;


	//Constructor-----------------------------------------------------

	public ReportRefereeController() {
		super();
	}

	//List------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Actor a = this.actorService.findByUserAccount(LoginService.getPrincipal());
		final int refereeId = a.getId();

		final Collection<Report> reports = this.reportService.findReportByRefereeId(refereeId);

		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("refereeId", refereeId);
		result.addObject("noteService", this.noteService);
		result.addObject("requestURI", "report/referee/list.do");

		return result;

	}

	//Show------------------------------------------------------------

	/*
	 * @RequestMapping(value = "/show", method = RequestMethod.GET)
	 * public ModelAndView show(@RequestParam final int reportId) {
	 * final ModelAndView result = new ModelAndView("report/edit");
	 * 
	 * final Report report = this.reportService.findOne(reportId);
	 * 
	 * result.addObject("report", report);
	 * result.addObject("isRead", true);
	 * result.addObject("requestURI", "/report/referee/show.do?actorId=" + reportId);
	 * 
	 * return result;
	 * 
	 * }
	 */

	//Create-----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int complaintId) {
		ModelAndView result;
		final Report report;

		final Actor referee = this.actorService.findByUserAccount(LoginService.getPrincipal());

		final Complaint complaint = this.complaintService.findOne(complaintId);
		if (complaint == null) {
			result = new ModelAndView("welcome/index");
			result.getModel().put("message1", "org.hibernate.validator.constraints.URL.message");
		} else {
			report = this.reportService.create(complaintId, referee.getId());
			result = this.createEditModelAndView(report);
		}
		return result;
	}

	//Edit-------------------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int reportId) {
		ModelAndView result;
		Report report;
		try {

			report = this.reportService.findOne(reportId);
			Assert.notNull(report);
			final int refereeId = this.refereeService.findByUseraccount(LoginService.getPrincipal()).getId();
			final Collection<Report> reports = this.reportService.findReportByRefereeId(refereeId);
			final boolean isYourReport = reports.contains(report);

			Assert.isTrue(isYourReport);

			result = this.createEditModelAndView(report);

		} catch (final Exception e) {
			result = new ModelAndView("welcome/index");
			result.getModel().put("message1", "org.hibernate.validator.constraints.URL.message");
		}

		return result;
	}
	//Save-------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;
		final Referee a = this.refereeService.findByUseraccount(LoginService.getPrincipal());
		if (binding.hasErrors())
			result = this.createEditModelAndView(report);
		else
			try {
				this.reportService.save(report);
				result = new ModelAndView("redirect:list.do?refereeId=" + a.getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(report, "report.commit.error");
			}
		return result;
	}

	//Delete----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid final Report report, final BindingResult binding) {
		ModelAndView result;
		final Referee a = this.refereeService.findByUseraccount(LoginService.getPrincipal());
		try {
			this.reportService.delete(report);
			result = new ModelAndView("redirect:list.do?refereeId=" + a.getId());
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(report, "report.commit.error");
		}
		return result;
	}

	//ModelAndView-----------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Report report) {
		ModelAndView result;

		result = this.createEditModelAndView(report, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Report report, final String message) {
		ModelAndView result;

		final Referee a = this.refereeService.findByUseraccount(LoginService.getPrincipal());
		result = new ModelAndView("report/edit");
		result.addObject("report", report);
		result.addObject("message1", message);
		result.addObject("isRead", false);
		result.addObject("refereeId", a.getId());
		result.addObject("requestURI", "report/referee/edit.do");

		return result;
	}
}

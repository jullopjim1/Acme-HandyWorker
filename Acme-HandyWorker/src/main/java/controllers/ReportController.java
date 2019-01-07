
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import domain.Report;

@Controller
@RequestMapping("/report")
public class ReportController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private ReportService	reportService;


	//Constructor---------------------------------------------------------

	public ReportController() {
		super();
	}
	//List ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int complaintId) {
		ModelAndView result;
		Report reports;

		reports = this.reportService.findReportFinal(complaintId);

		result = new ModelAndView("report/list");
		result.addObject("reports", reports);
		result.addObject("requestURI", "report/list.do");

		return result;
	}

}

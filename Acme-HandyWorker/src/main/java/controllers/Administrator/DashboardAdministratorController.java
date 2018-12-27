
package controllers.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.ApplicationService;
import services.ComplaintService;
import services.FixUpTaskService;
import services.ReportService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	//Services-----------------------------------------------------------

	@Autowired
	private FixUpTaskService	fixUpTaskService;

	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ReportService		reportService;

	@Autowired
	private ComplaintService	complaintService;


	//Constructor-------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	//Dashboard---------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		final ModelAndView modelAndView = new ModelAndView("dashboard");

		//QueryC1
		final Double avgC1 = this.fixUpTaskService.queryC1AVG();
		final Double maxC1 = this.fixUpTaskService.queryC1MAX();
		final Double minC1 = this.fixUpTaskService.queryC1MIN();
		final Double stddevC1 = this.fixUpTaskService.queryC1STDDEV();

		modelAndView.addObject("avgC1", avgC1);
		modelAndView.addObject("maxC1", maxC1);
		modelAndView.addObject("minC1", minC1);
		modelAndView.addObject("stddevC1", stddevC1);

		//QueryC2
		final Double avgC2 = this.applicationService.queryC2AVG();
		final Double maxC2 = this.applicationService.queryC2MAX();
		final Double minC2 = this.applicationService.queryC2MIN();
		final Double stddevC2 = this.applicationService.queryC2STDDEV();

		modelAndView.addObject("avgC2", avgC2);
		modelAndView.addObject("maxC2", maxC2);
		modelAndView.addObject("minC2", minC2);
		modelAndView.addObject("stddevC2", stddevC2);

		//QueryC3
		final Object[] queryC3 = this.fixUpTaskService.queryC3();
		modelAndView.addObject("avgC3", queryC3[0]);
		modelAndView.addObject("maxC3", queryC3[1]);
		modelAndView.addObject("minC3", queryC3[2]);
		modelAndView.addObject("stddevC3", queryC3[3]);

		//QueryC4
		final Object[] queryC4 = this.applicationService.queryC4();
		modelAndView.addObject("avgC4", queryC4[0]);
		modelAndView.addObject("maxC4", queryC4[1]);
		modelAndView.addObject("minC4", queryC4[2]);
		modelAndView.addObject("stddevC4", queryC4[3]);

		//QueryC5
		final Double queryC5 = this.applicationService.queryC5();
		modelAndView.addObject("queryC5", queryC5);

		//QueryC6
		final Double queryC6 = this.applicationService.queryC6();
		modelAndView.addObject("queryC6", queryC6);

		//QueryC7
		final Double queryC7 = this.applicationService.queryC7();
		modelAndView.addObject("queryC7", queryC7);

		//QueryC8
		final Double queryC8 = this.applicationService.queryC8();
		modelAndView.addObject("queryC8", queryC8);

		//QueryC9
		final Object[] queryC9 = this.applicationService.queryC9();
		modelAndView.addObject("queryC9", queryC9);

		//QueryC10
		final Object[] queryC10 = this.applicationService.queryC10();
		modelAndView.addObject("queryC10", queryC10);

		//QueryB1
		final Double avgB1 = this.fixUpTaskService.queryB1AVG();
		final Double maxB1 = this.fixUpTaskService.queryB1MAX();
		final Double minB1 = this.fixUpTaskService.queryB1MIN();
		final Double stddevB1 = this.fixUpTaskService.queryB1STDDEV();

		modelAndView.addObject("avgB1", avgB1);
		modelAndView.addObject("maxB1", maxB1);
		modelAndView.addObject("minB1", minB1);
		modelAndView.addObject("stddevB1", stddevB1);

		//QueryB2
		final Double avgB2 = this.reportService.queryB2AVG();
		final Double maxB2 = this.reportService.queryB2MAX();
		final Double minB2 = this.reportService.queryB2MIN();
		final Double stddevB2 = this.reportService.queryB2AVG();

		modelAndView.addObject("avgB2", avgB2);
		modelAndView.addObject("maxB2", maxB2);
		modelAndView.addObject("minB2", minB2);
		modelAndView.addObject("stddevB2", stddevB2);

		//QueryB3
		final Double queryB3 = this.fixUpTaskService.queryB3();
		modelAndView.addObject("queryB3", queryB3);

		//QueryB4
		final Object[] queryB4 = this.complaintService.queryB4();
		modelAndView.addObject("queryB4", queryB4);

		//QueryB5
		final Object[] queryB5 = this.fixUpTaskService.queryB5();
		modelAndView.addObject("queryB5", queryB5);

		return modelAndView;
	}

}

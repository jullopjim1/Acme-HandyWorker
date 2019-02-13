
package controllers.Administrator;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.ComplaintService;
import services.FixUpTaskService;
import services.ReportService;
import controllers.AbstractController;
import domain.Customer;
import domain.HandyWorker;

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
		final ModelAndView modelAndView = new ModelAndView("administrator/dashboard");
		final DecimalFormat df = new DecimalFormat("0.00");
		final String nulo = "---";
		//QueryC1
		final Double avgC1 = this.fixUpTaskService.queryC1AVG();
		final Double maxC1 = this.fixUpTaskService.queryC1MAX();
		final Double minC1 = this.fixUpTaskService.queryC1MIN();
		final Double stddevC1 = this.fixUpTaskService.queryC1STDDEV();

		if (avgC1 != null)
			modelAndView.addObject("avgC1", df.format(avgC1));
		else
			modelAndView.addObject("avgC1", nulo);

		if (avgC1 != null)
			modelAndView.addObject("maxC1", df.format(maxC1));
		else
			modelAndView.addObject("maxC1", nulo);

		if (minC1 != null)
			modelAndView.addObject("minC1", df.format(minC1));
		else
			modelAndView.addObject("minC1", nulo);

		if (stddevC1 != null)
			modelAndView.addObject("stddevC1", df.format(stddevC1));
		else
			modelAndView.addObject("stddevC1", nulo);

		//QueryC2
		final Double avgC2 = this.applicationService.queryC2AVG();
		final Double maxC2 = this.applicationService.queryC2MAX();
		final Double minC2 = this.applicationService.queryC2MIN();
		final Double stddevC2 = this.applicationService.queryC2STDDEV();

		if (avgC2 != null)
			modelAndView.addObject("avgC2", df.format(avgC2));
		else
			modelAndView.addObject("avgC2", nulo);

		if (maxC2 != null)
			modelAndView.addObject("maxC2", df.format(maxC2));
		else
			modelAndView.addObject("maxC2", nulo);

		if (minC2 != null)
			modelAndView.addObject("minC2", df.format(minC2));
		else
			modelAndView.addObject("minC2", nulo);

		if (stddevC2 != null)
			modelAndView.addObject("stddevC2", df.format(stddevC2));
		else
			modelAndView.addObject("stddevC2", nulo);

		//QueryC3
		final Object[] queryC3 = this.fixUpTaskService.queryC3();
		if (queryC3[0] != null && queryC3[1] != null && queryC3[2] != null && queryC3[3] != null) {
			modelAndView.addObject("avgC3", df.format(queryC3[0]));
			modelAndView.addObject("maxC3", df.format(queryC3[1]));
			modelAndView.addObject("minC3", df.format(queryC3[2]));
			modelAndView.addObject("stddevC3", df.format(queryC3[3]));
		} else {
			modelAndView.addObject("avgC3", nulo);
			modelAndView.addObject("maxC3", nulo);
			modelAndView.addObject("minC3", nulo);
			modelAndView.addObject("stddevC3", nulo);
		}

		//QueryC4
		final Object[] queryC4 = this.applicationService.queryC4();
		if (queryC4[0] != null && queryC4[1] != null && queryC4[2] != null && queryC4[3] != null) {
			modelAndView.addObject("avgC4", df.format(queryC4[0]));
			modelAndView.addObject("maxC4", df.format(queryC4[1]));
			modelAndView.addObject("minC4", df.format(queryC4[2]));
			modelAndView.addObject("stddevC4", df.format(queryC4[3]));
		} else {
			modelAndView.addObject("avgC4", nulo);
			modelAndView.addObject("maxC4", nulo);
			modelAndView.addObject("minC4", nulo);
			modelAndView.addObject("stddevC4", nulo);
		}

		//QueryC5
		final Double queryC5 = this.applicationService.queryC5();
		if (queryC5 != null)
			modelAndView.addObject("queryC5", df.format(queryC5));
		else
			modelAndView.addObject("queryC5", nulo);

		//QueryC6
		final Double queryC6 = this.applicationService.queryC6();
		if (queryC6 != null)
			modelAndView.addObject("queryC6", df.format(queryC6));
		else
			modelAndView.addObject("queryC6", nulo);
		//QueryC7
		final Double queryC7 = this.applicationService.queryC7();
		if (queryC7 != null)
			modelAndView.addObject("queryC7", df.format(queryC7));
		else
			modelAndView.addObject("queryC7", nulo);

		//QueryC8
		final Double queryC8 = this.applicationService.queryC8();
		if (queryC8 != null)
			modelAndView.addObject("queryC8", df.format(queryC8));
		else
			modelAndView.addObject("queryC8", nulo);
		//QueryC9
		final ArrayList<Customer> queryC9 = this.applicationService.queryC9();
		if (queryC9 != null)
			modelAndView.addObject("queryC9", queryC9);
		//QueryC10
		final ArrayList<HandyWorker> queryC10 = this.applicationService.queryC10();
		if (queryC10 != null)
			modelAndView.addObject("queryC10", queryC10);

		//QueryB1
		final Double avgB1 = this.fixUpTaskService.queryB1AVG();
		final Double maxB1 = this.fixUpTaskService.queryB1MAX();
		final Double minB1 = this.fixUpTaskService.queryB1MIN();
		final Double stddevB1 = this.fixUpTaskService.queryB1STDDEV();

		if (avgB1 != null)
			modelAndView.addObject("avgB1", df.format(avgB1));
		else
			modelAndView.addObject("avgB1", nulo);

		if (maxB1 != null)
			modelAndView.addObject("maxB1", df.format(maxB1));
		else
			modelAndView.addObject("avgB1", nulo);

		if (minB1 != null)
			modelAndView.addObject("minB1", df.format(minB1));
		else
			modelAndView.addObject("minB1", nulo);

		if (stddevB1 != null)
			modelAndView.addObject("stddevB1", df.format(stddevB1));
		else
			modelAndView.addObject("stddevB1", nulo);

		//QueryB2
		final Double avgB2 = this.reportService.queryB2AVG();
		final Double maxB2 = this.reportService.queryB2MAX();
		final Double minB2 = this.reportService.queryB2MIN();
		final Double stddevB2 = this.reportService.queryB2AVG();

		if (avgB2 != null)
			modelAndView.addObject("avgB2", df.format(avgB2));
		else
			modelAndView.addObject("avgB2", nulo);

		if (maxB2 != null)
			modelAndView.addObject("maxB2", df.format(maxB2));
		else
			modelAndView.addObject("maxB2", nulo);

		if (minB2 != null)
			modelAndView.addObject("minB2", df.format(minB2));
		else
			modelAndView.addObject("minB2", nulo);

		if (stddevB2 != null)
			modelAndView.addObject("stddevB2", df.format(stddevB2));
		else
			modelAndView.addObject("stddevB2", nulo);

		//QueryB3
		final Double queryB3 = this.fixUpTaskService.queryB3();
		if (queryB3 != null)
			modelAndView.addObject("queryB3", df.format(queryB3));
		else
			modelAndView.addObject("queryB3", nulo);

		//QueryB4
		final ArrayList<Customer> queryB4 = this.complaintService.queryB4();
		if (queryB4 != null)
			modelAndView.addObject("queryB4", queryB4);

		//QueryB5
		final ArrayList<HandyWorker> queryB5 = this.fixUpTaskService.queryB5();
		if (queryB5 != null)
			modelAndView.addObject("queryB5", queryB5);

		return modelAndView;
	}

}

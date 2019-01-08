
package controllers.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Configuration;
import services.ConfigurationService;

@Controller
@RequestMapping("/dashboard/administrator")
public class ConfigurationAdministratorController extends AbstractController {

	//Service----------------------------------------------------------------

	@Autowired
	private ConfigurationService configurationService;


	//Constructor------------------------------------------------------------

	public ConfigurationAdministratorController() {
		super();
	}

	//List-------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;

		final Configuration configuration = this.configurationService.findOne();

		result = new ModelAndView("configuration/list");
		result.addObject("configuration", configuration);
		result.addObject("requestURI", "configuration/administrator/list.do");
		return result;
	}

}

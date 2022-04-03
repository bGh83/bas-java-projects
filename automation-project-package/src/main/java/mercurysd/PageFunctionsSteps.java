package mercurysd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cucumber.api.java.en.And;
import managers.AutUtilities;
import managers.TestContext;
import mercurypo.PageFunctions;

public class PageFunctionsSteps {
	TestContext tc;
	PageFunctions pf;
	
	public PageFunctionsSteps(TestContext context) {
		tc = context;
		pf = tc.getPageObject().pageFunctions();
	}
	
	@And("enters following data in {string} page")
	public void enters_following_data_in_page(String page, io.cucumber.datatable.DataTable dataTable) {
		
		Map<String, String> jMap = new HashMap<>();
		jMap = (Map<String, String>) AutUtilities.call().pageObjectParser()
				.parse("/Users/basil/git/MercuryTours/Framework06152019/PageObjects.json");		
		Map<String, String> eMap = new HashMap<>();		
		List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);

		for (String key : list.get(0).keySet()) {
				eMap.put(jMap.get(key), list.get(0).get(key));				
		}		
		
		pf.enter_data(eMap);
		
	}
}

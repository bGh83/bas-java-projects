package stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import enums.Context;
import enums.StepData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pageobjs.GoogleMainPage;
import pageobjs.GoogleSearchResults;
import support.TestContext;

public class GoogleSearchResultsSteps extends CommonStepDefFunctions {

	private static Logger log = LogManager.getLogger(GoogleSearchResultsSteps.class.getName());
	private TestContext testContext;
	GoogleSearchResults googleSearchResults;
	
	public GoogleSearchResultsSteps (TestContext context) {
		super(context);
		testContext = context;
		googleSearchResults = testContext.getPageObjectManager().getGoogleSearchResultsPageObj();
	}
	

	@And("search result is displayed")
	public void search_result_is_displayed(DataTable dt) {
		setDataTable(dt);
				
		String str = (String)testContext.scenarioContext.getContext(Context.SEARCH);		
		log.info ("Searching for: {}", str);
		assertThat(googleSearchResults.isSearchResultsPresent(str))
			.withFailMessage("Not able to find keyword: "+str).isTrue();
		log.info("...OK");
		
		log.info ("Searching for: {}", getDataTable(StepData.SEARCH));
		assertThat(googleSearchResults.isSearchResultsPresent(getDataTable(StepData.SEARCH)))
			.withFailMessage("Not able to find keyword: "+getDataTable(StepData.SEARCH)).isTrue();
		log.info("...OK");
		
	}
	
}

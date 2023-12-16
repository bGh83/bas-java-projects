package stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import enums.Context;
import enums.StepData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import pageobjs.GoogleMainPage;
import support.TestContext;

public class GoogleMainPageSteps extends CommonStepDefFunctions {

	private static Logger log = LogManager.getLogger(GoogleMainPageSteps.class.getName());
	private TestContext testContext;
	GoogleMainPage googleMainPage;
	
	public GoogleMainPageSteps (TestContext context) {
		super(context);
		testContext = context;
		googleMainPage = testContext.getPageObjectManager().getGoogleMainPageObj();
	}
	
	@Given("google is opened")
	public void google_is_opened() {
		googleMainPage.openGoogle();
	}	
	
	@When("google search is done")
	public void google_search_is_done(DataTable dt) {
		setDataTable(dt);
		testContext.scenarioContext.setContext(Context.SEARCH, googleMainPage.search(getDataTable()));
	}
	
}

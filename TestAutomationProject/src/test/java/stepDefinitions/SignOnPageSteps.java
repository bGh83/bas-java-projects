package stepDefinitions;

import cucumber.TestContext;
import cucumber.api.java.en.Then;
import pageObjects.SignOnPage;

public class SignOnPageSteps {

	TestContext tc;
	SignOnPage sop;
	
	public SignOnPageSteps(TestContext context) {
		tc = context;
		sop = tc.getPageObjectManager().getSignOnPage();
	}
	
	@Then("^signon page is opened$")
	public void signon_page_is_opened()  {
		sop.is_SignOnPage();
	}

}

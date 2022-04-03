package mercurysd;

import cucumber.api.java.en.Then;
import managers.TestContext;
import mercurypo.SignOnPage;

public class SignOnPageSteps {

	TestContext tc;
	SignOnPage sop;
	
	public SignOnPageSteps(TestContext context) {
		tc = context;
		sop = tc.getPageObject().getSignOnPage();
	}
	
	@Then("^signon page is opened$")
	public void signon_page_is_opened()  {
		sop.is_SignOnPage();
	}

}

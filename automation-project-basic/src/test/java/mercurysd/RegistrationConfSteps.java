package mercurysd;

import org.junit.Assert;
import cucumber.api.java.en.Then;
import enums.Context;
import managers.TestContext;
import mercurypo.RegistrationConfPage;

public class RegistrationConfSteps {

	TestContext tc;
	RegistrationConfPage rcp;
	
	public RegistrationConfSteps(TestContext context) {
		tc = context;
		rcp = tc.getPageObject().getRegistrationConfPage();
	}

	@Then("^confirmation page is opened$")
	public void confirmation_page_is_opened() {
		String userName = (String) tc.sc.getContext(Context.USER_NAME);
		Assert.assertTrue("Sign-in link is not found",rcp.is_signin());
		Assert.assertTrue("User Name:["+userName+"] is not displayed.",rcp.is_userName(userName));
	}
}

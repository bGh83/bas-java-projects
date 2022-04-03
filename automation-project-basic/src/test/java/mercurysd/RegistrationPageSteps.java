package mercurysd;

import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import enums.Context;
import managers.FileHandlers;
import managers.TestContext;
import mercurydo.User;
import mercurypo.RegistrationPage;

public class RegistrationPageSteps {

	TestContext tc;
	RegistrationPage rp;
	
	public RegistrationPageSteps(TestContext context) {
		tc = context;
		rp = tc.getPageObject().getRegistrationPage();
	}
	
	@And("^enters basic information for customer \"([^\"]*)\"$")
	public void enters_basic_information_for_customer(String firstName)  {
		User user = FileHandlers.handle().jsonFile().getUserByName(firstName);
		rp.enter_basicInfo(user);
		tc.sc.setContext(Context.USER_NAME, user.userName);
		Assert.assertTrue(false);
	}
	
	@When("^submit is clicked$")
	public void submit_is_clicked() {
		rp.click_submit();
	}
	
	@Then("^registration page is opened$")
	public void registration_page_is_opened()  {
		rp.is_RegistrationPage();
	}

}

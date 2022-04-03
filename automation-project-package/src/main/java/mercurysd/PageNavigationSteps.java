package mercurysd;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import managers.TestContext;
import mercurypo.PageNavigation;

public class PageNavigationSteps {
	
	TestContext tc;
	PageNavigation nav;
	
	public PageNavigationSteps(TestContext context) {
		tc = context;
		nav = tc.getPageObject().pageNavigation();
	}

	@Given("^user opens home page$")
	public void user_opens_home_page()  {
		nav.open_HomePage();
	}

	@And("opens {string} page")
	public void opens_page(String string) {
		nav.open_Page(string, "Register: Mercury Tours");
	}	
	
}

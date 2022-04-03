/*Sharing Test/Scenario Context with all StepDefinition files
 * using PicoContainer (Dependency Injection Container)
 * Steps files are using the following:-
 * --PageObjects provided by PageObjectManager
 * --WebDriver provided by WebDriverManager
 * --Properties provided by FileReaderManager
 * --Scenario Data provided by Scenario Context
 * Implement as following in each step def file...
	
	TestContext tc;
	SignOnPage sop;
	
	public SignOnPageSteps(TestContext context) {
		tc = context;
		sop = tc.getPageObjectManager().getSignOnPage();
	}

*/

package managers;

public class TestContext {

	private WebDrivers wdm;
	private PageObjects pom;
	public ScenarioContext sc;
	
	public TestContext() {
		wdm = new WebDrivers();
		pom = new PageObjects(wdm.getDriver());
		sc = new ScenarioContext();
	}
	
	public WebDrivers getDriver() {
		return wdm;
	}
	
	public PageObjects getPageObject() {
		return pom;
	}
	
	public ScenarioContext getScenarioContext() {
		return sc;
	}

}

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

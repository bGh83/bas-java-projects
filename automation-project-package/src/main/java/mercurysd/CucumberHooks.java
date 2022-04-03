package mercurysd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import managers.TestContext;

public class CucumberHooks {

	TestContext tc;
	private static Logger log = LogManager.getLogger(CucumberHooks.class.getName());

	public CucumberHooks(TestContext context) {
		tc = context;
	}

	@Before
	public void BeforeSteps(Scenario scenario) {		
		log.info("Starting Scenario ["+scenario.getName()+"]");
	}

	@After
	public void AfterSteps(Scenario scenario) {

		WebDriver driver = tc.getDriver().getDriver();
		scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
		log.info("Ending Scenario ["+scenario.getName()+" ("+scenario.getStatus()+") "+"]");
		driver.quit();
	}

}

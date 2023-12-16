package stepdefs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import support.TestContext;

public class CucumberHooks {

	TestContext testContext;
	private static Logger log = LogManager.getLogger(CucumberHooks.class.getName());

	public CucumberHooks(TestContext context) {
		testContext = context;
	}

	@Before
	public void BeforeSteps() {
		log.info("\n++++++++++++++++++++++++++++");
	}

	@After
	public void AfterSteps() {
		testContext.getWebDriverManager().closeDriver();
	}
	
}

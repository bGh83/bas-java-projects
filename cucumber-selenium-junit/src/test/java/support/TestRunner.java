package support;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

//mvn clean test
//mvn test -Dcucumber.filter.tags="@google"

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "src/test/resources/features/",
	    plugin = {"summary", "html:target/cucumber-reports.html"},
		glue= {"stepdefs"}
		)

public class TestRunner {

}

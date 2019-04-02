package runner;

import java.sql.Connection;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import managers.FileHandlers;

@RunWith(Cucumber.class)
@CucumberOptions(
		//features = {"src/test/resources/application/features/ApplicationPages.feature:14"},
		features = {"src/test/resources/application/features/ApplicationPages.feature"},
		//features = {"src/test/resources/application/features/UserRegistration.feature"},
		//features = {"src/test/resources/application/features/"},
        glue = {"mercurysd"},                
        plugin = {"html:target/cucumber-reports", "json:target/cucumber-reports/Cucumber.json"}
)

public class RunnerTest {

}

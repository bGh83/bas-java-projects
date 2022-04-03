package mercurypo;

import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import managers.AutUtilities;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"./features"},  
		glue={"mercurysd"},
        plugin = {"pretty","html:target/cucumber-reports", "json:target/cucumber-reports/report.json"}
)

public class RunCukes {
	
	public static void main(String[] args) {
		AutUtilities.call().setLogging();
		JUnitCore.main(RunCukes.class.getName());
	}

}

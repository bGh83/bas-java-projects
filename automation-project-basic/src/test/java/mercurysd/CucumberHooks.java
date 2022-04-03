package mercurysd;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.sql.Connection;
import com.google.common.io.Files;
//import com.vimalselvam.cucumber.listener.Reporter;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import managers.FileHandlers;
import managers.TestContext;
import managers.WebDrivers;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;

public class CucumberHooks {

	TestContext tc;
	private static Logger log = LogManager.getLogger(CucumberHooks.class.getName());
	private static List<String> data;
	
	public CucumberHooks(TestContext context) {
		tc = context;
	}
	
	@Before
	public void BeforeSteps(Scenario scenario) {
		System.out.println("Starting test...");		
	}
	
	@After
	public void AfterSteps(Scenario scenario) {			
		
		//For sequential run, write test results to db for pipeline
		if (!System.getProperty("run").equals("para")) {
			data = new ArrayList<String>(6);			
			data.add(scenario.getName());
			data.add(scenario.getUri());			
			data.add(scenario.getLines().get(1).toString());
			String status = scenario.isFailed() ? "Failed":"Passed";
			data.add(status);
			data.add(System.getProperty("user.name"));
			
			Connection con = FileHandlers.handle().sqlLiteDb().openRunDataDB();
			FileHandlers.handle().sqlLiteDb().addRunData(con, data);
			FileHandlers.handle().sqlLiteDb().closeRunDataDB(con);
			data.clear();
		}
		
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll("\\s+", "_").toUpperCase();
			WebDriver driver =  tc.getDriver().getDriver();
			scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");			
						
			try {
				
				//Keep screenshots of failed examples/scenarios
				Random rand = new Random();								
				/*File sourcePath = ((TakesScreenshot) tc.getDriver().getDriver())
						.getScreenshotAs(OutputType.FILE);				
				File destinationPath = new File(FileHandlers.handle().configFile().getSnapshotPath()
						+ new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(Calendar.getInstance().getTime()).toString()
						+ "_" + System.getProperty("user.name")
						+ "_" + (rand.nextInt(99) + 1) + "_" + screenshotName + ".png");
				log.info("Hooks: Screenshot generated ["+destinationPath+"]");								
				Files.copy(sourcePath, destinationPath);
				
				// Attach screenshot to extent report if not parallel run
				if (!System.getProperty("run").equals("para")) {
					Reporter.addScreenCaptureFromPath(destinationPath.toString());
				}*/
				
			} catch (Exception e) {}
		}
		
		System.out.println("Ending Test...");
		tc.getDriver().closeDriver();
	}
	
}




//**************************************** Other Functions ****************************************

class jThread implements Runnable {

	Robot rb;
	private volatile boolean exit = false;
	private static Logger log = LogManager.getLogger(CucumberHooks.class.getName());
	
	@Override
	public void run() {

		while (!exit) {
			try {
				rb = new Robot();
				Thread.sleep(5000);
				log.info("Hooks: Starting thread - Credential entry with Robot");				
				//setClipBoardData(FileHandlers.handle().configFile().getCredentials(0));
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(2000);

				rb.keyPress(KeyEvent.VK_TAB);
				rb.keyRelease(KeyEvent.VK_TAB);

				//setClipBoardData(FileHandlers.handle().configFile().getCredentials(1));
				rb.keyPress(KeyEvent.VK_CONTROL);
				rb.keyPress(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_V);
				rb.keyRelease(KeyEvent.VK_CONTROL);
				Thread.sleep(2000);
				rb.keyPress(KeyEvent.VK_ENTER);
				Thread.sleep(2000);
			} catch (Exception e) {}
		}
	}

	public void stop() {		
		log.info("Hooks: Stopping thread - Credential entry with Robot");
		exit = true;
	}

	public void setClipBoardData(String data) {
		StringSelection ss = new StringSelection(data);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
	}

}

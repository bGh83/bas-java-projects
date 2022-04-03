package managers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//http://www.testautomationguru.com/selenium-webdriver-factory-design-pattern-using-java-8-supplier/

public class WebDrivers {

	private static final Map<String, Supplier<WebDriver>> driverMap = new HashMap<>();
	private static String browser;
	private static String driverPath;
	private WebDriver driver;

	// chrome driver supplier
	private static final Supplier<WebDriver> chromeDriverSupplier = () -> {
		System.setProperty("webdriver.chrome.driver", driverPath);
		return new ChromeDriver();
	};

	// firefox driver supplier
	private static final Supplier<WebDriver> firefoxDriverSupplier = () -> {
		System.setProperty("webdriver.gecko.driver", driverPath);
		return new FirefoxDriver();
	};

	// add more suppliers here

	// add all the drivers into a map
	static {
		driverMap.put("CHROME", chromeDriverSupplier);
		driverMap.put("FIREFOX", firefoxDriverSupplier);
	}

	// return a new driver from the map
	public final WebDriver getDriver() {	
		
		if (driver != null) {return driver;} 
				
		browser = AutUtilities.call().config().getProperty("BROWSER");
		driverPath = AutUtilities.call().config().getProperty("BROWSER_DRIVER_PATH")
			+ AutUtilities.call().config().getProperty(browser+"_DRIVER");
		driver = driverMap.get(browser).get();
		
		if (Boolean.valueOf(AutUtilities.call().config().getProperty("IS_MAXIMIZE"))) 
			driver.manage().window().maximize();
		
		driver.manage().timeouts().implicitlyWait(Integer.valueOf(
				AutUtilities.call().config().getProperty("IMPLICITLY_WAIT")),TimeUnit.SECONDS);
		
		return driver;
	}

}

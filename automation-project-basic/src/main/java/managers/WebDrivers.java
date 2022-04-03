package managers;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import enums.DriverType;
import enums.RunLocation;
import managers.FileHandlers;

public class WebDrivers {

	private WebDriver wd;
	private RemoteWebDriver rwd;
	private static DriverType dtyp;
	private static RunLocation rloc;
	private static Boolean isHeadless, isMaximize;
	private static String url, path;
	private static Long delay;
	private static Logger log = LogManager.getLogger(WebDrivers.class.getName());

	public WebDrivers() {

		dtyp = FileHandlers.handle().configFile().getBrowser();
		rloc = FileHandlers.handle().configFile().getRunLocation();
		// isHeadless = FileHandlers.handle().configFile().isHeadless();
		isMaximize = FileHandlers.handle().configFile().getBrowserWindowSize();
		delay = FileHandlers.handle().configFile().getImplicitlyWait();

		log.info("Key Configs:" + "rloc:" + rloc + ",dtyp:" + dtyp + ",isHeadless:"
				+ isHeadless + ",isMaximize:" + isMaximize);
	}

	public WebDriver getDriver() {
		if (wd == null)
			wd = createDriver();
		return wd;
	}

	private WebDriver createDriver() {

		log.debug("");

		switch (rloc) {
		case LOCAL:
			path = FileHandlers.handle().configFile().getDriverPath();
			log.debug(path);
			wd = createLocalDriver();
			break;
		case REMOTE:
			url = FileHandlers.handle().configFile().getHubUrl();
			log.info("Hub URL: " + url);
			wd = createRemoteDriver();
			break;
		}
		return wd;
	}

	private WebDriver createRemoteDriver() {

		// throw new RuntimeException("RemoteWebDriver is not yet implemented");
		// Capabilities chromeCapabilities = DesiredCapabilities.chrome();
		Capabilities fcap = DesiredCapabilities.firefox();
		RemoteWebDriver /* chrome, */ frwd = null;

		try {
			// chrome = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),
			// chromeCapabilities);
			frwd = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), fcap);
			// run against chrome
			// chrome.get("https://www.google.com");
			// System.out.println(chrome.getTitle());

			// run against firefox
			// firefox.get("https://www.google.com");
			// System.out.println(firefox.getTitle());

			// chrome.quit();
			// firefox.quit();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return frwd;
	}

	private WebDriver createLocalDriver() {

		switch (dtyp) {

		case FIREFOX:
			ProfilesIni p = new ProfilesIni();
			FirefoxProfile fp = p.getProfile("TestProfile");
			System.setProperty("webdriver.gecko.driver", path + "geckodriver.exe");
			FirefoxOptions fo = new FirefoxOptions();
			fo.setProfile(fp);
			if (isHeadless) fo.setHeadless(true);
			fo.setCapability(FirefoxOptions.FIREFOX_OPTIONS, fo);
			wd = new FirefoxDriver(fo);
			break;

		case CHROME:
			log.debug("");
			ChromeOptions co = new ChromeOptions();
			co.setCapability(ChromeOptions.CAPABILITY, co);
			System.setProperty("webdriver.chrome.driver", path + "chromedriver.exe");
			// if (isHeadless) co.setHeadless(true);
			wd = new ChromeDriver(co);
			break;

		case INTERNETEXPLORER:
			wd = new InternetExplorerDriver();
			break;

		case EDGE:
			System.setProperty("webdriver.edge.driver", path + "MicrosoftWebDriver.exe");
			wd = new EdgeDriver();
			break;
		}

		if (isMaximize) wd.manage().window().maximize();
		wd.manage().timeouts().implicitlyWait(delay,TimeUnit.SECONDS);
		return wd;
	}

	public void closeDriver() {
		wd.quit();
	}

}
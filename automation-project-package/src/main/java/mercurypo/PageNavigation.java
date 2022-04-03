package mercurypo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import managers.AutUtilities;

public class PageNavigation {
	
	WebDriver driver;
	private static Logger log = LogManager.getLogger(PageNavigation.class.getName());

	public PageNavigation (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public Boolean open_HomePage() {
		String url = AutUtilities.call().config().getProperty(AutUtilities.call().config().getProperty("WEBAPP")
				+ "_APP_URL");		
		driver.get(url);
		log.info("Open ["+driver.getCurrentUrl()+"]");
		return driver.getTitle().equals("Welcome: Mercury Tours");
	}
	
	public Boolean open_Page(String linkText, String title) {				
		driver.findElement(By.linkText(linkText)).click();
		log.info("Open ["+driver.getCurrentUrl()+"]");
		return driver.getTitle().equals(title);
	}

	
}

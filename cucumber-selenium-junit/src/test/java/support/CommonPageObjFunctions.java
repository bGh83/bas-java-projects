package support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public abstract class CommonPageObjFunctions extends CommonFunctions {
	
	private WebDriver driver;
	private static Logger log = LogManager.getLogger(CommonPageObjFunctions.class.getName());
	
	public CommonPageObjFunctions (WebDriver driver) {
		this.driver = driver;
	}
	
	public Boolean isDisplayed(Object obj) {
		if(obj instanceof String)
			return isDisplayed(driver, (String) obj);
		if(obj instanceof By)
			return isDisplayed(driver, (By) obj);
		if(obj instanceof WebElement)
			return isDisplayed(driver, (WebElement) obj);
		return false;
	}
	
	
}

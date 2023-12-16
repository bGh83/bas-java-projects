package support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

abstract class CommonFunctions {
 
	private static Logger log = LogManager.getLogger(CommonFunctions.class.getName());
	public CommonWebDriverFunctions comdf = CommonFunctionManager.getInstance().getCommonDriverFunctions();
			
	public CommonFunctions() {
 
	}	
			
	protected Boolean isDisplayed(WebDriver driver, String str) {
		return comdf.isDisplayed(driver, str);
	}
	
	protected Boolean isDisplayed(WebDriver driver, By locator) {
		return comdf.isDisplayed(driver, locator);
	}
	
	protected Boolean isDisplayed(WebDriver driver, WebElement element) {
		return comdf.isDisplayed(driver, element);
	}
	
}

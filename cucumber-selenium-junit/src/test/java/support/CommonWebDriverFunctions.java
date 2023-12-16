package support;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.google.common.base.Function;

public class CommonWebDriverFunctions {

	private static Logger log = LogManager.getLogger(CommonWebDriverFunctions.class.getName());
	
	public CommonWebDriverFunctions() {
		
	}
	
	public Boolean isDisplayed(WebDriver driver, String str) {
		return isDisplayed(driver, By.xpath("//*[contains(text(),'"+str+"')]"));
	}
	
	public Boolean isDisplayed (WebDriver driver, WebElement element) {
		try {
			
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.visibilityOf(element));
			highlightElement(driver, element);
			
			return true;
		} catch (Exception e) {
			log.warn(e.toString());
		}
		return false;
	}
		
	public Boolean isDisplayed (WebDriver driver, By locator) {
			
		try {
			
			// Setting FluentWait for list
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
			// Check for condition in every 2 seconds and overriding default interval 500 MS
			wait.pollingEvery(Duration.ofSeconds(2));
			
			WebElement webElement = wait.until(new Function<WebDriver, WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					log.info("*");
					return driver.findElement(locator);
				}
			});
			
			highlightElement(driver, webElement);
			return true;
			
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return false;
	}
	
	private void highlightElement(WebDriver driver, WebElement webElement) {	    
	    String originalStyle = webElement.getAttribute("style");
	    JavascriptExecutor js = (JavascriptExecutor) driver; 
	    js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", 
	    		webElement, "style", originalStyle + "background: yellow; border: 2px solid red;");
	    try {
			TimeUnit.MICROSECONDS.sleep(250);
		} catch (InterruptedException e) {}
	    
	    js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", 
	    		webElement, "style", originalStyle);
	}
	
}

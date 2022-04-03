package mercurypo;

import java.util.List;
import java.util.Map;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import managers.AutUtilities;

public class PageFunctions {

	WebDriver driver;

	public PageFunctions (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void enter_data (Map<String, String> eMap) {
		eMap.entrySet()
        .stream()
        .forEach(entry -> {
            List<WebElement> elements = driver.findElements(By.name(entry.getKey()));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elements.get(0));
            AutUtilities.call().elementHandler().handle(elements, entry.getValue());
        });
	}
	
}

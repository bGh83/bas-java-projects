package pageobjs;

import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import enums.StepData;
import support.CommonException;
import support.CommonPageObjFunctions;


public class GoogleMainPage extends CommonPageObjFunctions {
	
	private static Logger log = LogManager.getLogger(GoogleMainPage.class.getName());
	
	@FindBy(tagName = "textarea")
	private static WebElement txaSearch;
	
	@FindBy(xpath = "//input[@value='Google Search']")
	private static WebElement btnSearch;
		
	public GoogleMainPage (WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public void openGoogle() {
				
	}
	
	public String search(List<Map<StepData, String>> lmaps) {
		txaSearch.sendKeys(lmaps.get(0).get(StepData.SEARCH));
		
		if(!isDisplayed(btnSearch))
			throw new CommonException("Search button not found");
		
		btnSearch.click();		
		return lmaps.get(0).get(StepData.SEARCH);
	}
	
}

package pageobjs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import support.CommonException;
import support.CommonPageObjFunctions;


public class GoogleSearchResults extends CommonPageObjFunctions {
	
	private static Logger log = LogManager.getLogger(GoogleSearchResults.class.getName());
		
	public GoogleSearchResults (WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}
	
	public Boolean isSearchResultsPresent(String str) {
		try {

			if(!isDisplayed(str))
				throw new CommonException ("Not displayed");
			
			return true;
		} catch (Exception e) {
			log.error(e.toString());
		}
		
		return false;
	}
	
}

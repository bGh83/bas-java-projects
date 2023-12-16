package support;

import org.openqa.selenium.WebDriver;

import pageobjs.GoogleMainPage;
import pageobjs.GoogleSearchResults;

//to use single object for each page
public class PageObjectManager {

	private WebDriver driver;
	
	private GoogleMainPage googleMainPage;
	private GoogleSearchResults googleSearchResults;
	
	public PageObjectManager (WebDriver driver) {
		this.driver = driver;
	}
	
	public GoogleMainPage getGoogleMainPageObj() {
		return (googleMainPage == null) ? googleMainPage = new GoogleMainPage(driver) : googleMainPage; 
	}
	
	public GoogleSearchResults getGoogleSearchResultsPageObj() {
		return (googleSearchResults == null) ? googleSearchResults = new GoogleSearchResults(driver) : googleSearchResults; 
	}
}

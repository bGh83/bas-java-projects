package managers;

import org.openqa.selenium.WebDriver;

import mercurypo.PageFunctions;
import mercurypo.PageNavigation;

public class PageObjects {

	private WebDriver driver;
	private PageNavigation nav;
	private PageFunctions pf;
	
	public PageObjects (WebDriver driver) {
		this.driver = driver;		
	}
	
	public PageNavigation pageNavigation() {
		return (nav == null) ? nav = new PageNavigation(driver) : nav;
	}	
	
	public PageFunctions pageFunctions() {
		return (pf == null) ? pf = new PageFunctions(driver) : pf;
	}
	
}

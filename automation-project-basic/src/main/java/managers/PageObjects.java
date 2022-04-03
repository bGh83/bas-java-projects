/*Page Object Manger enables to use single object for all the step definition files.*/
/*Page Object Pattern - Every application page has a unique class*/

package managers;

import org.openqa.selenium.WebDriver;

import mercurypo.HomePage;
import mercurypo.RegistrationConfPage;
import mercurypo.RegistrationPage;
import mercurypo.SignOnPage;


public class PageObjects {

	private WebDriver driver;
	private HomePage hp;
	private SignOnPage sop;
	private RegistrationPage rp;
	private RegistrationConfPage rcp;
	
	public PageObjects (WebDriver driver) {
		this.driver = driver;
		//Add the following line to create the object
		//PageObjectManager pom = new PageObjectManager(driver);
	}
	
	//Create an object of page class only if the object is null
	public HomePage getHomePage() {
		return (hp == null) ? hp = new HomePage(driver) : hp;
	}
	
	public SignOnPage getSignOnPage() {
		return (sop == null) ? sop = new SignOnPage(driver) : sop;
	}
	
	public RegistrationPage getRegistrationPage() {
		return (rp == null) ? rp = new RegistrationPage(driver) : rp;
	}
	
	public RegistrationConfPage getRegistrationConfPage() {
		return (rcp == null) ? rcp = new RegistrationConfPage(driver) : rcp;
	}
	
}

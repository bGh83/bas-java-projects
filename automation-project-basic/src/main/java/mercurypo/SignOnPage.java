package mercurypo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class SignOnPage {

	WebDriver driver;
	
	public SignOnPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean is_SignOnPage() {
		Boolean flag = false;
		if (driver.getTitle().equals("Sign-on: Mercury Tours")) {
			flag = true;
		}
		return(flag);
	}

}

package mercurypo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class RegistrationConfPage {

	WebDriver driver;
	
	@FindBy(how = How.LINK_TEXT, using="sign-in")
	private WebElement link_signin;
	
	public RegistrationConfPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public Boolean is_signin() {
		Boolean flag = false;
		flag = link_signin.isDisplayed();
		return flag;
	}

	public Boolean is_userName(String userName) {
		Boolean flag = false;
		flag = driver.findElement(By.xpath("//*[contains(text(),'"+userName+"')]")).isDisplayed();
		return flag;
	}
}

package mercurypo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import mercurydo.User;

public class RegistrationPage {

	WebDriver driver;
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(how = How.NAME, using="firstName")
	private WebElement tbx_firstName;
	
	@FindBy(how = How.NAME, using="lastName")
	private WebElement tbx_lastName;
	
	@FindBy(how = How.NAME, using="phone")
	private WebElement tbx_phone;
	
	@FindBy(how = How.NAME, using="userName")
	private WebElement tbx_email;

	@FindBy(how = How.NAME, using="address1")
	private WebElement tbx_address1;
	
	@FindBy(how = How.NAME, using="address2")
	private WebElement tbx_address2;
	
	@FindBy(how = How.NAME, using="city")
	private WebElement tbx_city;
	
	@FindBy(how = How.NAME, using="state")
	private WebElement tbx_state;
	
	@FindBy(how = How.NAME, using="postalCode")
	private WebElement tbx_postalCode;
	
	@FindBy(how = How.NAME, using="country")
	private WebElement cbx_country;
	
	@FindBy(how = How.NAME, using="email")
	private WebElement tbx_userName;
	
	@FindBy(how = How.NAME, using="password")
	private WebElement tbx_password;
	
	@FindBy(how = How.NAME, using="confirmPassword")
	private WebElement tbx_confirmPassword;
	
	@FindBy(how = How.NAME, using="register")
	private WebElement btn_register;

	public void click_submit() {
		btn_register.click();	
	}
	
	public void enter_basicInfo(User user) {
		
		tbx_firstName.sendKeys(user.firstName);
		tbx_lastName.sendKeys(user.lastName);
		tbx_phone.sendKeys(user.phone);
		tbx_email.sendKeys(user.email);
		tbx_address1.sendKeys(user.address1);
		tbx_address2.sendKeys(user.address2);
		tbx_city.sendKeys(user.city);
		tbx_state.sendKeys(user.state);
		tbx_postalCode.sendKeys(user.postalCode);
		cbx_country.sendKeys(user.country);
		tbx_userName.sendKeys(user.userName);
		tbx_password.sendKeys(user.password);
		tbx_confirmPassword.sendKeys(user.confirmPassword);
		
	}
	
	public Boolean is_RegistrationPage() {
		Boolean flag = false;
		if (driver.getTitle().equals("Register: Mercury Tours")) {
			flag = true;
		}
		return(flag);
	}
}

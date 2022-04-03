package mercurypo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import managers.FileHandlers;

public class HomePage {

	WebDriver driver;
	private static Logger log = LogManager.getLogger(HomePage.class.getName());

	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(how = How.LINK_TEXT, using = "REGISTER")
	private WebElement link_register;

	@FindBy(how = How.NAME, using = "userName")
	private WebElement txb_userName;

	@FindBy(how = How.NAME, using = "password")
	private WebElement txb_password;

	@FindBy(how = How.NAME, using = "login")
	private WebElement btn_Login;

	public void click_register() {
		link_register.click();
	}

	public void type_userName(String name) {
		txb_userName.sendKeys(name);
	}

	public void type_password(String password) {
		txb_password.sendKeys(password);
	}

	public void click_login() {
		btn_Login.click();
	}

	public void open_HomePage() {
		log.debug("");
		driver.get(FileHandlers.handle().configFile().getApplicationUrl());
	}

	public Boolean is_HomePage() {
		Boolean flag = false;
		if (driver.getTitle().equals("Welcome: Mercury Tours")) {
			flag = true;
		}

		log.debug("debugging"); // when actions such as click, sendkeys, getText() are performed
		// change root level to "trace" in xml to print all messages.
		log.info("info"); // when operation is successful. opposite usage for log.error
		log.error("--error00--"); // when elements are not displayed in the page or if any validations fail
		log.fatal("--fatal00--");

		return (flag);
	}
}

package support;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import enums.Browser;
import enums.Environment;

public class WebDriverManager {

	private WebDriver driver;
	private static Browser driverType;
	private static Environment environmentType;
	
	public WebDriverManager() {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}

	public WebDriver getDriver() {
		if(driver == null) driver = createDriver();
		return driver;
	}

	private WebDriver createDriver() {
		   switch (environmentType) {	    
	        case LOCAL : driver = createLocalDriver();
	        	break;
	        default:
	        	break;	      
		   }
		   return driver;
	}


	private WebDriver createLocalDriver() {
        
		switch (driverType) {	    
       
        case EDGE : 
        	System.setProperty("webdriver.edge.driver",FileReaderManager.getInstance().getConfigReader().getDriverPath());
    		driver = new EdgeDriver();
    		driver.manage().window().maximize();
    		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    		driver.get(FileReaderManager.getInstance().getConfigReader().getURL());
    		break;
    		
		default:
			break;
        
        }
        
		return driver;
	}	

	public void closeDriver() {
		driver.close();
		driver.quit();
	}
	
	
	
}

package support;

import java.io.FileReader;
import java.util.Properties;
import enums.Browser;
import enums.Environment;

public class ConfigFileReader {
	
	private Properties props;	
	
	public ConfigFileReader() {		
		try {		
			FileReader reader = new FileReader("./src/test/resources/configurations.properties");
			props = new Properties();
			props.load(reader);
        
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public String getDriverPath(){
		return props.getProperty("driverPath");				
	}
	
	public String getURL(){
		return props.getProperty("url");				
	}
	
	public Browser getBrowser() {
		return (props.getProperty("browser") == "chrome" ? Browser.CHROME : Browser.EDGE);
	}

	public Environment getEnvironment() {
		return (props.getProperty("environment") == "remote" ? Environment.REMOTE : Environment.LOCAL);
	}
	
	public String getLogConfig() {
		return props.getProperty("log4j2xml");
	}
	
}

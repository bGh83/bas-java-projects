package handlers;

//Config File
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import enums.DriverType;
import enums.RunLocation;

public class ConfigFile {

	private Properties prop;
	private final String propertyFilePath= "src/test/resources/Configuration.properties";

	public ConfigFile(){
		BufferedReader reader;
		//Load .properties File
		try {
			reader = new BufferedReader(new FileReader(propertyFilePath));
			prop = new Properties();
			try {
				prop.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
		} 
	}

	public String getDriverPath(){
		String driverPath = prop.getProperty("driverPath");
		if(driverPath!= null) return driverPath;
		else throw new RuntimeException("Config Error: driverPath."); 
	}

	public long getImplicitlyWait() { 
		String implicitlyWait = prop.getProperty("implicitlyWait");
		if(implicitlyWait != null) return Long.parseLong(implicitlyWait);
		else throw new RuntimeException("Config Error: implicitlyWait."); 
	}

	public String getApplicationUrl() {
		String url = prop.getProperty("url");
		if(url != null) return url;
		else throw new RuntimeException("Config Error: url.");
	}

	public DriverType getBrowser() {
		String browserName = prop.getProperty("browser");
		if(browserName == null || browserName.equals("chrome")) return DriverType.CHROME;
		else if(browserName.equalsIgnoreCase("firefox")) return DriverType.FIREFOX;
		else if(browserName.equals("iexplorer")) return DriverType.INTERNETEXPLORER;
		else throw new RuntimeException("Config Error: browser: (" + browserName +")");
	}

	public RunLocation getRunLocation() {
		String environmentName = prop.getProperty("environment");
		if(environmentName == null || environmentName.equalsIgnoreCase("local")) return RunLocation.LOCAL;
		else if(environmentName.equals("remote")) return RunLocation.REMOTE;
		else throw new RuntimeException("Config Error: environment: (" + environmentName + ")");
	}

	public Boolean getBrowserWindowSize() {
		String windowSize = prop.getProperty("windowMaximize");
		if(windowSize != null) return Boolean.valueOf(windowSize);
		return true;
	}
	
	public String getDataPath(){
		String testDataResourcePath = prop.getProperty("dataLoc");
		if(testDataResourcePath!= null) return testDataResourcePath;
		else throw new RuntimeException("Config Error: test data resource.");		
	}
	
	public String getHubUrl(){
		String url = prop.getProperty("hubUrl");
		if(url!= null) return url;
		else throw new RuntimeException("Config Error: Hub Url.");		
	}
	
	public String getResourcePath(){
		String loc = prop.getProperty("resourcePath");
		if(loc!= null) return loc;
		else throw new RuntimeException("Config Error: Resource Path.");		
	}

}

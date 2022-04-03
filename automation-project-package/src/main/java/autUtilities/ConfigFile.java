package autUtilities;

import java.io.File;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigFile {

	private Configuration config;

	public String getProperty(String param) {
		String val = null;
		if(System.getProperty(param) == null) {
			val = config.getString(param);
		} else {
			val = System.getProperty(param);
		}
		
		return val;
	}
		
	public ConfigFile() {
		Configurations configs = new Configurations();		
		try {
			
			String loc = null;
			if(System.getProperty("CONFIG") == null) {
				loc = System.getProperty("user.dir")+"/configuration.properties";
			} else {
				loc = System.getProperty("CONFIG");
			}

		    config = configs.properties(new File(loc));
		    		    
		} catch (ConfigurationException cex) {cex.printStackTrace();}
	}


}

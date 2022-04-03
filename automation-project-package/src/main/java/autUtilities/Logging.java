package autUtilities;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import managers.AutUtilities;

public class Logging {

	public Logging() {
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File(AutUtilities.call().config().getProperty("LOG4J2_XML_PATH"));
		 
		// this will force a reconfiguration
		context.setConfigLocation(file.toURI());
	}
	
}

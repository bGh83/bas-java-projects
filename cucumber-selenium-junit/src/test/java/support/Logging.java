package support;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

public class Logging {

	public Logging() {
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File(FileReaderManager.getInstance().getConfigReader().getLogConfig());
		 
		// this will force a reconfiguration
		context.setConfigLocation(file.toURI());
	}
	
}

package managers;

import autUtilities.ConfigFile;
import autUtilities.ElementHandler;
import autUtilities.Logging;
import autUtilities.PageObjectParser;

public class AutUtilities {
	
	private static AutUtilities util = new AutUtilities();
	private static ConfigFile cfg;
	private static Logging log;
	private static PageObjectParser pobj;
	private static ElementHandler ele;

	private AutUtilities() {}
	
	public static AutUtilities call() {
		return util;
	}
	
	public ConfigFile config() {
		return (cfg == null) ? new ConfigFile() : cfg; 
	}
	
	public Logging setLogging() {
		return (log == null) ? new Logging() : log; 
	}
	
	public PageObjectParser pageObjectParser() {
		return (pobj == null) ? new PageObjectParser() : pobj; 
	}
	
	public ElementHandler elementHandler() {
		return (ele == null) ? new ElementHandler() : ele; 
	}
	
}

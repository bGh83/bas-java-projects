/*Singleton Design Pattern limits object creation to one*/

package managers;

import handlers.ConfigFile;
import handlers.GSONFileReader;
import handlers.SQLLiteDB;
import handlers.XMLFile;

public class FileHandlers {
	
	private static FileHandlers fh = new FileHandlers();
	private static ConfigFile cfh;
	private static GSONFileReader jfh;
	private static XMLFile xfh;
	private static SQLLiteDB ltdb;
	
	//prevent user from creating instances
	private FileHandlers() {
	}
	
	//maintain static reference to own instance
	public static FileHandlers handle() {
		return fh;
	}

	//use getInstance() method to access public methods
	public ConfigFile configFile() {
		return (cfh == null) ? new ConfigFile() : cfh; 
	}
	
	public GSONFileReader jsonFile() {
		return (jfh == null) ? new GSONFileReader() : jfh; 
	}
	
	public XMLFile xmlFile() {
		return (xfh == null) ? new XMLFile() : xfh; 
	}
	
	public SQLLiteDB sqlLiteDb() {
		return (ltdb == null) ? new SQLLiteDB() : ltdb; 
	}
}

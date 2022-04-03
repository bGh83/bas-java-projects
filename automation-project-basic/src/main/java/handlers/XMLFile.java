package handlers;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import managers.FileHandlers;

//Contains functions to handle specific XML templates

public class XMLFile {

	private static DocumentBuilderFactory dbFactory;
	private static DocumentBuilder dBuilder;
	private static NodeList nl;
	private static Node n;
	private static TransformerFactory transformerFactory;
	private static Transformer transformer;
	private static DOMSource source;
	private static StreamResult result;
	private static StringWriter strWriter;
	private static Document tdoc, ddoc;	
	private static String xfp, xfp_temp;
	private static String template, data;
	private static Logger log = LogManager.getLogger(XMLFile.class.getName());
	
	public XMLFile() {								
	}
	
	public Document generateXML(String XMLData) {
		data = XMLData;		
		dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			ddoc = dBuilder.parse(new InputSource(new StringReader(data)));
		} catch (Exception e) {	e.printStackTrace();}
			return ddoc;	
	}
	
	//https://www.journaldev.com/1237/java-convert-string-to-xml-document-and-xml-document-to-string
	public String getNodeVal(Document doc, String node, int loc) {		
		nl = doc.getElementsByTagName(node);		
		n = nl.item(loc).getFirstChild();
		String value = n.getNodeValue();
		log.debug(node+"["+n.getNodeValue()+"]");
		return value;
	}
	
	public Document readXMLFile(String XMLFileName) {
		try {
			
			template = XMLFileName;
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();

			
			//Read XMl Template
			xfp = FileHandlers.handle().configFile().getResourcePath();
			tdoc = dBuilder.parse(new File(xfp+template));
			
			tdoc.getDocumentElement().normalize();
		} catch (Exception e) { e.printStackTrace();}		
		return tdoc;
	}

	public Document updateNodeVal(Document doc, String node, int loc, String data) {		
		nl = doc.getElementsByTagName(node);
		// e =(Element) nl.item(6);
		// n = e.getFirstChild();
		n = nl.item(loc).getFirstChild();		
		n.setNodeValue(data);
		log.debug(node+"["+n.getNodeValue()+"]");
		return doc;
	}
	
	public Document updateNodeVal(Document doc, String main_tag, int loc, String sub_tag, String data) {
	//public Document updateXMLNodeByElement(Document doc, String main_tag, int loc, String sub_tag, String data) {
		nl = doc.getElementsByTagName(main_tag);
		n = ((Element) nl.item(loc)).getElementsByTagName(sub_tag).item(0).getFirstChild();		
		n.setNodeValue(data);
		//System.out.println(n.getNodeValue());
		log.debug(main_tag+"."+sub_tag+"["+n.getNodeValue()+"]");
		return doc;
	}
	
	//http://www.topjavatutorial.com/java/java-programs/pretty-print-xml-java/
	public String prettyPrint(Document doc) {
		transformerFactory = TransformerFactory.newInstance();		
		strWriter = new StringWriter();
		
		try {
			transformer = transformerFactory.newTransformer();
			/*transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");*/
			transformer.setOutputProperty(OutputKeys.METHOD, "html");
			source = new DOMSource(doc);		
			result = new StreamResult(strWriter);
			transformer.transform(source, result);
			//writeNewXML(doc);
		} catch (TransformerException e) { e.printStackTrace();}	
		
		return strWriter.getBuffer().toString();
	}

	public String writeAsXMLFile(Document doc) {

		String fileName = null;
		
		try {
			// write the updated document to file or console
			doc.getDocumentElement().normalize();
			transformerFactory = TransformerFactory.newInstance();
			transformer = transformerFactory.newTransformer();
			source = new DOMSource(doc);
			Random rand = new Random();
			xfp_temp = FileHandlers.handle().configFile().getResourcePath();			
			xfp_temp = xfp_temp + new SimpleDateFormat("yyyy_MM_dd_HHmmss").format(Calendar.getInstance().getTime()).toString()
					+ "_" + System.getProperty("user.name")
					+ "_" + (rand.nextInt(99) + 1) + "_" + template;
			
			result = new StreamResult(new File(xfp_temp));
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
			
			//System.out.println("XML file updated successfully");
			log.debug("XML File ["+xfp_temp+"]");
			
			fileName = xfp_temp; 
			
			template = null;
			doc = null;
			xfp_temp = null;			
			
		} catch (Exception e) { e.printStackTrace();}
		
		return fileName; 
	}
}

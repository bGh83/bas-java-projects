package handlers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CucumberJSONParser {

	private static String jsonData;
	private static Map<String, List<String>> runStatMap;
	private static List<String> runStatList;
	
	//read file
	public static String readFile(String path) {
						
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		
		FileInputStream fileInputStream = null;
		String data="";				

		for (File file : listOfFiles) {
		    if ((file.isFile()) && (!file.isDirectory()) && 
		    		(FilenameUtils.getExtension(file.getName()).equals("json"))) {	
		    	StringBuffer stringBuffer = new StringBuffer("");
		    	String buff = "";
		    	
		        try{
				    fileInputStream=new FileInputStream(path+file.getName());
				    int i;				    
				    while((i=fileInputStream.read())!=-1) {
				        stringBuffer.append((char)i);}				    				    					   
				    	buff = stringBuffer.toString();				    					    					    				    
				} catch(Exception e){e.printStackTrace();}
				finally{
				    if(fileInputStream!=null){  
				        try {
							fileInputStream.close();
						} catch (IOException e) {e.printStackTrace();}}
				}		
		        
		        buff = buff.substring(2);
		        buff = buff.substring(0,buff.length()-2)+",";	
		        data = buff;
		    }
		    		    	    	
		}		
		
		data = "[" + data.substring(0,data.length()-1) + "]";
		
		return data;
	}
	
	public static void parseJSONData () {

		JSONParser parser = new JSONParser();		
		ArrayList<String> arrl = new ArrayList<String>();

		//dump every step status details (feature|scenario|step|status) to a list for later analysis
		try {
			
			JSONArray arr = (JSONArray) parser.parse(jsonData);			
			for (int i=0; i<arr.size();i++) {
				JSONObject ob = (JSONObject) arr.get(i);
				
				JSONArray earr = (JSONArray) ob.get("elements");													
				for (int j=0; j<earr.size();j++) {
					JSONObject eob = (JSONObject) earr.get(j);											
					
					JSONArray sarr = (JSONArray) eob.get("steps");
					for (int k=0; k<sarr.size();k++) {						
						JSONObject sobj = (JSONObject) sarr.get(k);
						
						JSONObject robj = (JSONObject) sobj.get("result");
						String [] path = ob.get("uri").toString().split("/");
						String src = "";
						String feature = path[path.length-1];
						for (int l=0;l<path.length-1;l++) {
							src = src+"/"+path[l];
						}
																		
						arrl.add(src+"|"+feature+"|"+eob.get("name")+"|"+sobj.get("name")+"|"+robj.toJSONString().split("\"status\":\"")[1].replace("\"}",""));
						
					}																	
				}												
			}

			List<String> rdata = new ArrayList<String>(5);			
			for (int i=0;i<arrl.size();i++) {																
				rdata.add(arrl.get(i).split("\\|")[0]); //Source
				rdata.add(arrl.get(i).split("\\|")[1]);	//Feature
				rdata.add(arrl.get(i).split("\\|")[2]);	//Scenario				
				rdata.add(arrl.get(i).split("\\|")[3]); //Step
				rdata.add(arrl.get(i).split("\\|")[4]);	//Status

				genRunDataMap(rdata);
				genRunTable(rdata);
				
				rdata.clear();
			}												
			
		} catch (ParseException e) {e.printStackTrace();}
	}
	
	public static void genRunTable (List<String> rdata) {
		runStatList.add(rdata.get(0)+"|"+rdata.get(1)+"|"+rdata.get(2)+"|"+rdata.get(3)+"|"+rdata.get(4));		
	}
	
	public static void genRunHtml () {
		
		StringBuilder buf = new StringBuilder();
		
		buf.append("<html>"+

				"<head>"+
				"<style>"+
				"table, th, td {"+
				"border: 1px solid black;"+
				"border-collapse: collapse;"+
				"}"+
				"th, td {"+
				"padding: 5px;"+
				"text-align: left;"+    
				"}"+
				"</style>"+
				"</head>"+			
				"<body>"+
				"<table style=\"width:100%\">"+
				"<tr>"+
				"<th>Folder</th>"+
				"<th>Feature</th>"+
				"<th>Scenario</th>"+
				"<th>Step</th>"+
				"<th>Status</th>"+
				"</tr>");
						
		for (int i=0; i<runStatList.size();i++) {
			
			String stat = runStatList.get(i).split("\\|")[4];
			
			buf.append("<tr><td>")
				.append(runStatList.get(i).split("\\|")[0])
				.append("</td><td>")
				.append(runStatList.get(i).split("\\|")[1])
				.append("</td><td>")
				.append(runStatList.get(i).split("\\|")[2])
				.append("</td><td>")
				.append(runStatList.get(i).split("\\|")[3])
				.append("</td><td>");
				
				if(stat.equals("failed")) {
					buf.append("<font color=red>")
					.append(runStatList.get(i).split("\\|")[4])								
					.append("</font></td></tr>");
				} else {
					buf.append(runStatList.get(i).split("\\|")[4])
					.append("</td></tr>");
				}
		}
		
		buf.append("</table>"+
				"</body>"+
				"</html>");
		
		Document doc = Jsoup.parse(buf.toString());
		final File f = new File("filename.html");
        try {
			FileUtils.writeStringToFile(f, doc.outerHtml(), "UTF-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
        
	}
	
	public static void genRunDataMap (List<String> rdata) {
		if (runStatMap.containsKey(rdata.get(2))) {
			if (runStatMap.get(rdata.get(2)).equals("passed") && rdata.get(4).equals("failed")) {
				runStatMap.put(rdata.get(2),Arrays.asList("failed",rdata.get(0)+"."+rdata.get(1)+"."+rdata.get(2)+"."+rdata.get(3)));
			}						
		} else {
			runStatMap.put(rdata.get(2),Arrays.asList(rdata.get(4),rdata.get(0)+"."+rdata.get(1)+"."+rdata.get(2)+"."+rdata.get(3)));
		}
		
	}
	
	public static void genRunSummary() {
		
		int passed =0;
		int failed =0;
		
		for (String scenario : runStatMap.keySet()) {
			if (runStatMap.get(scenario).get(0).equals("passed")) {
				passed = passed + 1;
			} else {
				failed = failed + 1;
			}
		}
            System.out.println("Passed ["+passed+"], Failed ["+failed+"]"); 
	}
	
	public static void genRunResults () {
		jsonData = readFile(System.getProperty("user.dir")+"/");
		runStatMap =  new HashMap<String, List<String>>();
		runStatList = new ArrayList<String>(5);
		parseJSONData();
		genRunSummary();
	}
	
	public static void main(String[] args) {
																			
		genRunResults();

	}

}

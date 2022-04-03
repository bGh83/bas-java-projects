package handlers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.util.ArrayList;
import java.util.List;

public class SQLLiteDB {

	public SQLLiteDB() {
		// TODO Auto-generated constructor stub
	}

	public void initRunDataTbl (Connection con) {
		try {
			Statement statement = con.createStatement();
			statement.setQueryTimeout(30);
			//statement.executeUpdate("drop table if exists run_data");
			
			statement.executeUpdate(
					
				"CREATE TABLE IF NOT EXISTS RUN_DATA (" 
				+"NAME 			STRING,"
				+"PATH 			STRING,"
				+"STEPS 		INTEGER," 
				+"STATUS 		STRING,"
				+"USER 			STRING,"
				+"TIMESTAMP 	DATETIME NOT NULL DEFAULT "
								+"(strftime('%Y-%m-%d %H:%M:%f', 'now', 'localtime')))");
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public Connection openRunDataDB() {
		Connection con = null;
		try {		
			Class.forName("org.sqlite.JDBC");			
			con = DriverManager.getConnection("jdbc:sqlite:.\\src\\test\\resources\\RunDataDB.db");						
		} catch (Exception e) {e.printStackTrace();}
		return con;
	}
	
	public ResultSet queryRunData(Connection con, String query) {
		ResultSet rs = null;
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(query);
		} catch (Exception e) {e.printStackTrace();}
		return rs;
	}
	
	public void addRunData(Connection con, List<String> data) {
		
		try {
			
			Statement st = con.createStatement();	
			
			st.executeUpdate(
					
					"INSERT INTO RUN_DATA (NAME, PATH, STEPS, STATUS, USER)" 
						+"VALUES('"
						+data.get(0)+"','"
						+data.get(1)+"','"
						+data.get(2)+"','"
						+data.get(3)+"','"
						+data.get(4)+"')");	
			
		} catch (Exception e) {e.printStackTrace();}
		
	}
	
	public void clearRunData(Connection con) {
		try {			
			Statement st = con.createStatement();
			initRunDataTbl (con);
			st.executeUpdate("DELETE FROM RUN_DATA");	
			//st.executeUpdate("VACCUM");
			
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public void closeRunDataDB(Connection con) {
		try {
			if (con != null) con.close();
		} catch (Exception e) {e.printStackTrace();}
	}
/*
	public static void main(String[] args) {

		ResultSet rs = null;
		Connection con = openRunHistoryDB();		
				
		List<String> data = new ArrayList<String>(6);
		data.add("test1");
		data.add("test2");
		data.add("0");
		data.add("test3");
		data.add("test4");

		addRunHistory(con, data);
		
		try {						
			rs = selectRunHistoryTbl(con, "select * from run_data");			
			System.out.println("sid"+"\t"
					+"path"+ "\t"
					+"steps"+"\t"
					+"status"+"\t"
					+"runby"+"\t"
					+"timestamp");
			
			while (rs.next()) {
				// read the result set
				System.out.println(rs.getString("sid")+"\t"
						+rs.getString("path")+"\t"
						+rs.getInt("steps")+"\t"
						+rs.getString("status")+"\t"
						+rs.getString("runby")+"\t"
						+rs.getString("timestamp"));
			}
						
		} catch (Exception e) {e.printStackTrace();}
		
		closeRunHistoryDB(con);		 		
	}*/
}

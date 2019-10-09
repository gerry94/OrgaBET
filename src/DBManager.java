import java.sql.*;
import java.util.*;

public class DBManager{
	
	private final int port;
	private final String usr, pwd, dbname;
	Connection conn;
	
	public DBManager(int port, String usr, String pwd, String dbname)
	{
		this.port = port;
		this.usr = usr;
		this.pwd = pwd;
		this.dbname = dbname;
		conn = null;
	}
	
	public void start()
	{
		String connStr = "jdbc:mysql://localhost:" + this.port + "/" + this.dbname +"?user=" + this.usr + "&password=" + this.pwd + "&useSSL=false";
		try {
			conn = DriverManager.getConnection(connStr);
			//System.out.println("Connection established.");
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void stop()
	{
		try {
			conn.close();
			//System.out.println("Connection closed.");
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public String login(String userid)
	{
		String query = "SELECT Name FROM User WHERE idUser = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userid);
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			if(rs.next())
				return rs.getString("Name");
			else return "";
		} catch (SQLException e) { e.printStackTrace(); }
		
		return "";
	}
	
	public List<String> list()
	{
		String query = "SELECT * FROM Catalogue";
		List<String> resultstr = new ArrayList<String>();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			while(rs.next())
			{
				resultstr.add(rs.getString("idBook"));
				resultstr.add(rs.getString("Title"));
				resultstr.add(rs.getString("Author"));
				String aval = "(Available)"; 
				if(rs.getString("Available").compareTo(Integer.toString(0)) == 0)
					aval = "(Not Available)";
				
				resultstr.add(aval);
			}
			
			return resultstr;
			
		}
		catch(SQLException e) { e.printStackTrace(); }
		
		return null;
	}

}

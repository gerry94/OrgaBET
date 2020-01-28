import java.sql.*;
import java.sql.Date;
import java.util.*;

public class DBManager{
	
	final String REDC="\033[0;31m", GREENC="\033[0;32m", ENDC="\033[0m";
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
		String connStr = "jdbc:mysql://localhost:" + this.port + "/" + this.dbname +"?user=" + this.usr + "&password=" + this.pwd + "&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
		try {
			conn = DriverManager.getConnection(connStr);
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void stop()
	{
		try {
			conn.close();
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
	
	public boolean check_privilege(String userid) 
	{
	    String query = "SELECT Privilege FROM User WHERE idUser = ?";
	    try
	    {
		      PreparedStatement ps = conn.prepareStatement(query);
		      ps.setString(1, userid);
		      ps.execute();
		      
		      ResultSet rs = ps.getResultSet();
		      
		      if(rs.next())
		    	  return rs.getBoolean("Privilege");
		      else return false;

	    } catch (SQLException e) {e.printStackTrace();}
	    
	    return false;
	}
}

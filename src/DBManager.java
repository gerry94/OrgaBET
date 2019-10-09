import java.sql.*;

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
		String connStr = "jdbc:mysql://localhost:" + this.port + "/" + this.dbname +"?user=" + this.usr + "&password=" + this.pwd;
		try {
			conn = DriverManager.getConnection(connStr);
			System.out.println("Connection established.");
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	public void stop()
	{
		try {
			conn.close();
			System.out.println("Connection closed.");
		} catch (SQLException e) { e.printStackTrace(); }
	}

}

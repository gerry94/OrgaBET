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
	
	public List<String> list(boolean privilege)
	{
		String query = "SELECT B.idBook, B.Title, B.Author, B.numCopies - COUNT(L.idBook) AS Available, B.numCopies AS Total FROM Book B LEFT JOIN Loan L ON B.idBook = L.idBook GROUP BY B.idBook ORDER BY B.idBook;";
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
				
				String aval=""; 
				if(rs.getString("Available").compareTo(Integer.toString(0)) == 0)
					aval = "(Not Available)";
				else
				{
					if(privilege) aval = "("+rs.getInt("Available")+"/"+rs.getString("Total")+" Available)";
					else aval = "(Available)";
				}
				
				resultstr.add(aval);
			}
			return resultstr;
			
		}
		catch(SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public int availability(String bookId) //1: success, 0: not avail, -1: invalid code
	{
		String query = "SELECT B.numCopies - COUNT(L.idBook) AS Available FROM Book B LEFT JOIN Loan L 	ON B.idBook = L.idBook WHERE B.idBook=?";
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, bookId);
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			if(rs.next())
				return rs.getInt("Available");
			else return -1;
		}
		catch(SQLException e) { e.printStackTrace(); }
		return -1;
	}
	
	public List<String> getBookInfo(String bookId)
	{
		String query = "SELECT Title, Author FROM Book WHERE idBook=?";
		List<String> resultstr = new ArrayList<String>();
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, bookId);
			ps.execute();
			
			ResultSet rs = ps.getResultSet();
			
			while(rs.next())
			{
				resultstr.add(rs.getString("Title"));
				resultstr.add(rs.getString("Author"));
			}
			
			return resultstr;
			
		}
		catch(SQLException e) { e.printStackTrace(); }
		
		return null;
	}
	
	public boolean borrow(String bookId, String usrId)
	{
		String query2 = "INSERT INTO Loan VALUES (?, ?, ?, ?)";
		
		Date current = new Date(System.currentTimeMillis());
		Date delivery = new Date(current.getTime()+31l*24l*60l*60l*1000l); //adding 1 month (in millisec) to borrow date
		
		try
		{			
			PreparedStatement ps2 = conn.prepareStatement(query2);
			
			ps2.setString(1, usrId);
			ps2.setString(2, bookId);
			ps2.setString(3, current.toString());
			ps2.setString(4, delivery.toString());
			
			int check = ps2.executeUpdate();
			ps2.close();
			if (check==1)
				return true;
		}
		catch(SQLIntegrityConstraintViolationException e) { 
			System.out.println(REDC + "ERROR: You already borrowed this book." + ENDC);
		} catch(SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	public boolean bookReturn (String bookId, String usrId)
	{
		String query = "DELETE FROM Loan WHERE idUser=? AND idBook=?";
		
		try
		{			
			PreparedStatement ps = conn.prepareStatement(query);
			
			ps.setString(1, usrId);
			ps.setString(2, bookId);
			
			int check=ps.executeUpdate();
			ps.close();
			if (check==1)
					return true;
		}catch(SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	public void createuser(String idUser, String name, String surname)
	{
			String query = "INSERT INTO User VALUES (?, ?, ?, 0)";
			
			try
			{
				PreparedStatement ps = conn.prepareStatement(query);
				ps.setString(1, idUser);
				ps.setString(2, name);
				ps.setString(3, surname);
				int insertedrows=ps.executeUpdate();
				if (insertedrows==1)
					System.out.println(GREENC + "User registration was successful." + ENDC);
				ps.close();
								
			}
			catch(SQLIntegrityConstraintViolationException e) { 
				System.out.println(REDC + "Failed to create user: This user is already registered." + ENDC);
			} catch(SQLException e) { e.printStackTrace(); }
		
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
	
	public boolean checkBookExistance(String idBook)
	{
		String query = "SELECT * FROM Book WHERE idBook=?";
		try {
				  PreparedStatement ps = conn.prepareStatement(query);
				  ps.setString(1, idBook);
				  ps.execute();
				  
				  ResultSet rs = ps.getResultSet();
				
				if(rs.next()) return true;
				else return false;
			
		} catch(SQLException e) { e.printStackTrace(); }
		return false;
	}
	
	public void addBook(String idBook, String Title, String Author, int numCopies) 
	{
	    String query = "INSERT INTO Book VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE numCopies=numCopies+?";
	    
	    try {
		      PreparedStatement ps = conn.prepareStatement(query);
		      ps.setString(1, idBook);
		      ps.setString(2, Title);
		      ps.setString(3, Author);
		      ps.setInt(4, numCopies);
		      ps.setInt(5, numCopies);
		      
		      int insertedrows = ps.executeUpdate();
		      if (insertedrows == 1)
		    	  System.out.println(GREENC + "Successfully added book to the Catalogue." + ENDC);
	    } 
	    catch(SQLIntegrityConstraintViolationException e) { 
			System.out.println(REDC + "Failed to add book: This book ID is already in use." + ENDC);
		} catch(SQLException e) { e.printStackTrace(); }
	 }
	
	public int getNumCopies(String idBook)
	{
		String query = "SELECT numCopies FROM Book WHERE idBook=?";
		try {
		      PreparedStatement ps = conn.prepareStatement(query);
		      ps.setString(1, idBook);
		      ps.execute();
		      ResultSet rs = ps.getResultSet();
		      
		      if(rs.next())
		    	  return rs.getInt("numCopies");
		      else return -1;
		} catch(SQLException e) { e.printStackTrace(); }
		return -1;
	}
	
	public void removeBook(String idBook, int removeCopies)
	{
	    int avail = availability(idBook);
	    int totalCopies = getNumCopies(idBook);
	    
	    if((avail == -1) || (totalCopies == -1)) {
	    	System.out.println(REDC + "ERROR: Invalid book ID!" + ENDC);
	    	return;
	    }
	    else if(avail == 0) {
	    	System.out.println(REDC + "ERROR: All copies are currently borrowed." + ENDC);
	    	return;
	    }
	    
	    String query="";
	    
	    if(avail-removeCopies < 0)
	    	System.out.println(REDC + "ERROR: Not enough copies available right now." + ENDC);
	    else
	    {
	    	try {
	    		PreparedStatement ps;
	    		
	    	if((avail-removeCopies == 0) && (removeCopies == totalCopies))
	    	{
	    		query ="DELETE FROM Book WHERE idBook=?";
	    		ps = conn.prepareStatement(query);
	    		ps.setString(1, idBook);
	    	}
	    	else 
	    	{
	    		query = "UPDATE Book SET numCopies=numCopies-? WHERE idBook=?";
	    		ps = conn.prepareStatement(query);
	    		ps.setInt(1, removeCopies);
	    		ps.setString(2, idBook);
	    	}
				int rows = ps.executeUpdate();
				  if (rows == 1)
					  System.out.println(GREENC + "Successfully removed book copy from the Catalogue." + ENDC);
	      } catch(SQLException e) { e.printStackTrace(); }
	    }
	    } 
}

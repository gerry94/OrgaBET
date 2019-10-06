package ex1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ex1 {

	public static void main(String[] args) {
		String connStr ="jdbc:mysql://localhost:3306/ex1?user=Khaeros&password=password";
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connStr);
			String ins="INSERT INTO Employee SET companyName= ?, address= ?, totalEmployee= ?, website= ?";
			PreparedStatement prpd=conn.prepareStatement(ins);
			prpd.setString(1,"Unipi");
			prpd.setString(2,"Via Diotisalvi");
			prpd.setString(3,"5000");
			prpd.setString(4,"info@unipi.it");
			
			prpd.setString(5,"Orgallica");
			prpd.setString(6,"Via Tenis");
			prpd.setString(7,"1");
			prpd.setString(8,"info@mrorga.it");
			
			prpd.setString(9,"Orga");
			prpd.setString(10,"Via Tenisse");
			prpd.setString(11,"0");
			prpd.setString(12,"info@mrorgallo.it");
			int insrows=prpd.executeUpdate();
			System.out.println("rowsinserted: "+ insrows);
			prpd.close();
			conn.close();
		} catch (SQLException ex) {
			System.out.println("SQLException: "+ ex.getMessage());
			System.out.println("SQLState: "+ ex.getSQLState());
			System.out.println("VendorError: "+ ex.getErrorCode());
		}

	}

}

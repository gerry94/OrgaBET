package sample;

import java.sql.*;

public class DBManager {
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
        String query = "SELECT Name FROM user WHERE idUser = ?";
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
}
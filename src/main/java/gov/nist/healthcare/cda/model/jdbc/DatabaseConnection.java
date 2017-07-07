/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.nist.healthcare.cda.model.jdbc;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mccaffrey
 */
public class DatabaseConnection {
    
    
    private static Connection con;
    private static Statement stmt;
    private boolean successfulConnection = false;
    
    /*
    public DatabaseConnection(Configuration config) throws SQLException {
        this.setHostname("localhost");
        this.setConfig(config);
        this.initialize();
    }
     */
    /** Creates a new instance of JdbcConnection */
    public DatabaseConnection() throws SQLException {
        //this.setConfig(config);
        this.initialize();
    }
    private void initialize() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = null; 
            
            url = "jdbc:mysql://localhost/test?user=dummy&serverTimezone=UTC";
            
            System.out.println("Connecting to mysql on url " + url);
            try {
          //      if(this.getConfig().getLogDatabaseUsername() != null) {
          //          con = DriverManager.getConnection(url, this.getConfig().getLogDatabaseUsername(), this.getConfig().getLogDatabasePassword());
          //      } else {
                    con = DriverManager.getConnection(url);                    
          //      }
            } catch (Exception e) {
                e.printStackTrace();
            }
            stmt = con.createStatement();
            setSuccessfulConnection(true);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            e.printStackTrace();
            setSuccessfulConnection(false);
        }
        
     //   GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON test.* TO 'dummy'@'localhost';
    
        
        
    }
    public void close() throws SQLException {
        con.close();
    }
        
    public ResultSet executeQuery(String sql) throws SQLException {
        ResultSet result = null;
        result = stmt.executeQuery(sql);
        return result;
    }
    
    public int executeUpdate(String sql) throws SQLException {
        int i = 0;
        i = stmt.executeUpdate(sql);
        return i;
    }
 public boolean isAlive() throws SQLException {
        return !con.isClosed();
    }
    
    public static String makeSafe(String input) {
        if(input == null)
            return "";
        String output = input.replaceAll("'", "\"");
        return output;
    }

    /**
     * @return the successfulConnection
     */
    public boolean isSuccessfulConnection() {
        return successfulConnection;
    }

    /**
     * @param successfulConnection the successfulConnection to set
     */
    public void setSuccessfulConnection(boolean successfulConnection) {
        this.successfulConnection = successfulConnection;
    }
    
    
    public static final void main (String[] args) {
        
        try {
            DatabaseConnection dbCon = new DatabaseConnection();
            dbCon.executeQuery("SELECT * FROM WORLD");
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        
        
    }

}

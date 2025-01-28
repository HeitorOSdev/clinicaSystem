package br.ufrn.tads.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "admin";
//    private static final String URL_DB = "jdbc:postgresql://localhost:5432/clinicadb";

    public static Connection getConnection() {
        Connection conn = null; // a default null connection
        try {
            //conn = DriverManager.getConnection(DBconnection.URL_DB, DBconnection.USER, DBconnection.PASSWORD); // server password
            
        	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinicadb", "postgres", "admin");
        	
        	if (conn != null) {
                System.out.println("Connected to database #1");
                
                
                
                
            }
        } catch (SQLException e) {
            System.out.println("Error when connecting...: " + e); //e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return conn;
    }
}

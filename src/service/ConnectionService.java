package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionService {
    private Connection connection;

    public ConnectionService() {
        int loginAttempts = 0;

        try {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException ex) {
            System.out.println("registerDriver fail");
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
    }

    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    public boolean connect(String username, String password)
    {
        String connectURL = "jdbc:oracle:thin:@localhost:1522:ug";

        try {
            connection = DriverManager.getConnection(connectURL,username,password);

            System.out.println("\nConnected to Oracle!");
            return true;
        } catch (SQLException ex) {
            System.out.println("connection fail");
            System.out.println("Message: " + ex.getMessage());
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
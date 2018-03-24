import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    private Connection connection;

    private int MAX_LOGIN_ATTEMPTS = 3;

    public DBConnection() {
        try {
            // Load the Oracle JDBC driver
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        } catch (SQLException ex) {
            System.out.println("registerDriver fail");
            System.out.println("Message: " + ex.getMessage());
            System.exit(-1);
        }
        connect("ora_j2z8", "a31484116");
    }


    /*
     * connects to Oracle database named ug using user supplied username and password
     */
    private boolean connect(String username, String password)
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
}
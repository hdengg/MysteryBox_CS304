package service;

import model.Customer;
import model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by janicelee on 2018-03-24.
 */
public class CustomerService {
    private Connection connection;

    public CustomerService(Connection connection) {
        this.connection = connection;
    }

    // returns true if customer login username and password match, false otherwise
    public boolean login(Session session, String username, String password) {
        Customer customer = getCustomer(username);
        if (customer != null && password.equals(customer.getPassword())) {
            session.loginCustomer(customer);
            return true;
        }
        return false;
    }

    // returns a customer given the username
    public Customer getCustomer(String username) {
        PreparedStatement pstmt;
        ResultSet rs;
        Customer customer;

        try {
            pstmt = connection.prepareStatement("SELECT * FROM Customer WHERE username = ?");
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if(rs.next()) {
                String cUsername = rs.getString("username");
                String cPassword = rs.getString("password");
                String cfirstName = rs.getString("first_name");
                String clastName = rs.getString("last_name");
                String cPhoneNum = rs.getString("phone");
                String cEmail = rs.getString("email");

                return new Customer(cUsername, cPassword, cfirstName, clastName, cPhoneNum, cEmail);
            }

            pstmt.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }

        return null;
    }

    // updates customer profile information
    public Customer updateCustomer(String username, String password, String firstName, String lastName, String phone, String email) {
        PreparedStatement pstmt;
        ResultSet rs;
        Customer customer;

        try {
            pstmt = connection.prepareStatement(
                    "UPDATE Customer SET password = ?, first_name = ?, last_name = ?, phone = ?, email = ? WHERE username = ?"
            );
            pstmt.setString(1, password);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, username);

            pstmt.executeUpdate();
            pstmt.close();
            return getCustomer(username);
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return null;
    }

}

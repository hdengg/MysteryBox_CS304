package service;

import model.Customer;
import model.Session;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        try {
            Customer customer = getCustomer(username);
            if (customer != null && password.equals(customer.getPassword())) {
                session.loginCustomer(customer);
                return true;
            }

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return false;
    }

    // returns a customer given the username, null otherwise
    public Customer getCustomer(String username) throws SQLException {
        PreparedStatement pstmt;
        ResultSet rs;

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
        return null;
    }

    // returns a list of all customers
    public ArrayList<Customer> getAllCustomers() throws SQLException {
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer;

        pstmt = connection.prepareStatement("SELECT * FROM Customer");
        rs = pstmt.executeQuery();

        while(rs.next()) {
            String cUsername = rs.getString("username");
            String cPassword = rs.getString("password");
            String cfirstName = rs.getString("first_name");
            String clastName = rs.getString("last_name");
            String cPhoneNum = rs.getString("phone");
            String cEmail = rs.getString("email");

            customer = new Customer(cUsername, cPassword, cfirstName, clastName, cPhoneNum, cEmail);
            customers.add(customer);
        }
        pstmt.close();
        return customers;
    }

    // updates customer profile information
    public Customer updateCustomer(String username, String password, String firstName, String lastName,
                                   String phone, String email) throws SQLException {
        PreparedStatement pstmt;

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
            connection.commit();
            pstmt.close();
            return getCustomer(username);

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                throw ex2;
            }
            throw ex;
        }
    }
}

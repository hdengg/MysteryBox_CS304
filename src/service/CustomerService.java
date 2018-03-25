package service;

import java.sql.Connection;

/**
 * Created by janicelee on 2018-03-24.
 */
public class CustomerService {
    private Connection connection;

    public CustomerService(Connection connection) {
        this.connection = connection;
    }

    // returns true if customer login username and password match, false otherwise
    public boolean login(String username, String password) {


        // login successful
        return true;
    }

    // updates password
    public void updatePassword() {

    }

    // updates firstName
    public void updateFirstName() {

    }

    // updates lastName
    public void updateLastName() {

    }

    // updates phoneNum
    public void updatePhoneNum() {

    }

    // updates email
    public void updateEmail() {

    }
}

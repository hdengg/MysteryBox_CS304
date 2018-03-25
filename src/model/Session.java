package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Session {
    private Customer customer;
    boolean loggedIn = false;

    public Session() {}

    public void loginCustomer(Customer customer) {
        this.customer = customer;
        loggedIn = true;
    }

    public Customer getCustomer() {
        return customer;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
}

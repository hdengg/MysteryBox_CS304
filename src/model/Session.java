package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Session {
    private Customer customer;
    boolean loggedIn = false;

    public Session() {}

    public Customer getCustomer() {
        return customer;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void loginCustomer(Customer customer) {
        setCustomer(customer);
        loggedIn = true;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }


}

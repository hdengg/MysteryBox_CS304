package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Session {
    protected Customer customer;
    protected boolean loggedIn = false;
    protected boolean isAdmin = false;
    private static Session sessionInstance;

    private Session() {}

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

    public void setAdmin() {
        Customer admin = new Customer("admin", "hackathon", "Will", "Smith", "1234567890", "admin@gmail.com");
        this.customer = admin;
        isAdmin = true; }

    public boolean isAdmin() {return isAdmin;}

    public static Session getInstance() {
        if  (sessionInstance == null)
            sessionInstance = new Session();
        return sessionInstance;
    }

}

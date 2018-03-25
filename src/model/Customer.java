package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Customer {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNum;
    private String email;

    public Customer(String username, String password, String firstName, String lastName, String phoneNum, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public String getEmail() {
        return email;
    }


}

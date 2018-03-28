package client;

import model.Address;
import model.Customer;
import model.Model;
import service.AddressService;
import service.CreditCardService;
import service.CustomerService;
import ui.CustomerUI;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountController {
    private Customer customer;
    private CustomerService customerService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private CustomerUI customerUI;

    public AccountController(CustomerService customerService, AddressService addressService,
                             CreditCardService creditCardService, CustomerUI customerUI) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.creditCardService = creditCardService;
        this.customerUI = customerUI;
        customerUI.setController(this);
        customer = customerService.getLoggedInCustomer();

    }

    public void setProfilePanel() {
        customerUI.getFirstNameLbl().setText("First name: " + customer.getFirstName());
        customerUI.getLastNameLbl().setText("Last name: " + customer.getLastName());
        customerUI.getUsernameLbl().setText("Username: " + customer.getUsername());
        customerUI.getEmailLbl().setText("Email: " + customer.getEmail());
        customerUI.getPhoneLbl().setText("Phone number: " + customer.getPhoneNum());
    }

    public void setAddressPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"House No.", "Street No.", "Postal Code", "City", "Province"};
        dtm.setColumnIdentifiers(header);
        customerUI.getAddressTable().setModel(dtm);

        try {
            ArrayList<Address> addresses = addressService.getAllCustomerAddresses(customer.getUsername());
            for (int i = 0; i < addresses.size(); i++) {
                Address address = addresses.get(i);
                dtm.addRow(new Object[] {address.getHouseNum(), address.getStreet(), address.getPostalCode(),
                        address.getCity(), address.getProvince()});
            }
        } catch (SQLException e) {
            // TODO: error dialog
        }


    }


}

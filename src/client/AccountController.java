package client;

import model.Address;
import model.Customer;
import service.AddressService;
import service.CreditCardService;
import service.CustomerService;
import ui.AddressUI;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountController extends FrameController {
    private Customer customer;
    private CustomerService customerService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private MainUI mainUI;
    private AddressUI addressUI;

    public AccountController(CustomerService customerService, AddressService addressService,
                             CreditCardService creditCardService, MainUI mainUI) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.creditCardService = creditCardService;
        this.mainUI = mainUI;
        mainUI.addAccountController(this);
        customer = customerService.getLoggedInCustomer();
        initListeners();

    }

    public void setProfilePanel() {
        mainUI.getFirstNameLbl().setText("First name: " + customer.getFirstName());
        mainUI.getLastNameLbl().setText("Last name: " + customer.getLastName());
        mainUI.getUsernameLbl().setText("Username: " + customer.getUsername());
        mainUI.getEmailLbl().setText("Email: " + customer.getEmail());
        mainUI.getPhoneLbl().setText("Phone number: " + customer.getPhoneNum());
    }

    public void setAddressPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"House No.", "Street No.", "Postal Code", "City", "Province"};
        dtm.setColumnIdentifiers(header);
        mainUI.getAddressTable().setModel(dtm);

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

    private class addressEditBtnListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame editAddressFrame = new JFrame("Edit Address");
            addressUI = new AddressUI();
            editAddressFrame.setContentPane(addressUI.getRootPanel());
            setFrameProperties(editAddressFrame);
            editAddressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            //TODO: think about coupling issues
        }
    }

    private void initListeners() {
        mainUI.getAddressEditBtn().addActionListener(new addressEditBtnListner());
    }

}

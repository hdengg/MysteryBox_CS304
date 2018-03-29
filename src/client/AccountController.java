package client;

import model.Address;
import model.CreditCard;
import model.Customer;
import model.Session;
import service.AddressService;
import service.ConnectionService;
import service.CreditCardService;
import service.CustomerService;
import ui.AddressUI;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AccountController extends FrameController {
    private Customer customer;
    private CustomerService customerService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private MainUI mainUI;
    private AddressUI addressUI;

    public AccountController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
        initListeners();

        customer = Session.getInstance().getCustomer();

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
            //TODO: error message
        }
    }

    public void setCreditCardPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Card ID", "Expiry Date", "Type", "Last Digits"};
        dtm.setColumnIdentifiers(header);
        mainUI.getCreditCardTable().setModel(dtm);

        try {
            ArrayList<CreditCard> creditCards = creditCardService.getCustomerCreditCards(customer.getUsername());

        } catch (SQLException e) {

        }

    }



    private class addressEditBtnListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame editAddressFrame = new JFrame("Edit Address");
            addressUI = new AddressUI();
            editAddressFrame.setContentPane(addressUI.getRootPanel());
            setFrameProperties(editAddressFrame);
            editAddressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private class cardEditBtnListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFrame editCardFrame = new JFrame("Edit Credit Cards");
            addressUI = new AddressUI();
            editCardFrame.setContentPane(addressUI.getRootPanel());
            setFrameProperties(editCardFrame);
            editCardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.customerService = new CustomerService(conn);
        this.creditCardService = new CreditCardService(conn);
        this.addressService = new AddressService(conn);
    }

    private void initListeners() {
        mainUI.getAddressEditBtn().addActionListener(new addressEditBtnListner());
        mainUI.getAddressEditBtn().addActionListener(new cardEditBtnListner());

    }

}

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
import ui.CardUI;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AccountController extends FrameController {
    private Customer customer;
    private String username;
    private CustomerService customerService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private MainUI mainUI;
    private AddressUI addressUI;
    private CardUI cardUI;
    private JFrame editJFrame;

    public AccountController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
        //initListeners();

        customer = Session.getInstance().getCustomer();
        username = customer.getUsername();

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
        String[] header = new String[] {"House No.", "Street", "Postal Code", "City", "Province"};
        dtm.setColumnIdentifiers(header);
        mainUI.getAddressTable().setModel(dtm);

        try {
            ArrayList<Address> addresses = addressService.getAllCustomerAddresses(username);
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
            ArrayList<CreditCard> cards = creditCardService.getCustomerCreditCards(username);
            for (int i = 0; i < cards.size(); i++) {
                CreditCard card = cards.get(i);
                dtm.addRow(new Object[] {card.getCid(), card.getExpDate(), card.getType(), card.getLastDigits()});
            }

        } catch (SQLException e) {
            //TODO: error message
        }
    }

    public void createCreditCardEditWindow() {
        JFrame editCreditFrame = new JFrame("Edit Credit Cards");
        cardUI = new CardUI();
        editCreditFrame.setContentPane(cardUI.getRootPanel());
        setFrameProperties(editCreditFrame);
        editCreditFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void createAddressEditWindow() {
        Address currAddress = getCurrentAddress();

        JFrame editAddressFrame = new JFrame("Edit Address");
        addressUI = new AddressUI();
        addressUI.setAddressFields(currAddress);
        editAddressFrame.setContentPane(addressUI.getRootPanel());
        setFrameProperties(editAddressFrame);
        editAddressFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private Address getCurrentAddress() {
        JTable addressTable = mainUI.getAddressTable();
        int row = addressTable.getSelectedRow();

        ArrayList<String> data = new ArrayList<>();

        for (int i = 0; i < addressTable.getColumnCount(); i++) {
            data.add(addressTable.getModel().getValueAt(row, i).toString());
            //System.out.println(addressTable.getModel().getValueAt(row, i).toString());
        }

        int house_num = Integer.parseInt(data.get(0));
        String street = data.get(1);
        String postal_code = data.get(2);
        String city = data.get(3);
        String province = data.get(4);

        return new Address(house_num, street, postal_code, city, province);
    }

//    private CreditCard getCurrentCreditCard() {
//        JTable creditCardTable = mainUI.getCreditCardTable();
//        int row = creditCardTable.getSelectedRow();
//        if (creditCardTable.getSelectedRow() == -1) {
//            mainUI.getCardErrTxtField().setText("Error: No rows selected");
//            return null;
//        } else {
//            ArrayList<String> data = new ArrayList<>();
//
//            for (int i = 0; i < creditCardTable.getColumnCount(); i++) {
//                data.add(creditCardTable.getModel().getValueAt(row, i).toString());
//            }
//
//            System.out.println(data);
//
//            int cid = Integer.parseInt(data.get(0));
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String unParsedDate = data.get(1);
//            System.out.println(data.get(1));
//            Date parsedDate = null;
//            try {
//                parsedDate = dateFormat.parse(unParsedDate);
//            } catch (ParseException e) {
//                mainUI.getCardErrTxtField().setText("Error: Incorrect format of expiry date. Must be in format" +
//                        " yyyy-MM-dd");
//            }
//            java.sql.Date expDate = new java.sql.Date(parsedDate.getTime());
//            String token = CardController.randomTokenGenerator();
//            String type = data.get(2);
//            int lastDigits = Integer.parseInt(data.get(3));
//            return new CreditCard(cid, expDate, token, type, lastDigits);
//        }
//    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.customerService = new CustomerService(conn);
        this.creditCardService = new CreditCardService(conn);
        this.addressService = new AddressService(conn);
    }


}
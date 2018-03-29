package client;

import model.Address;
import model.Customer;
import model.Session;
import service.AddressService;
import service.ConnectionService;
import ui.AddressUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddressController {
    private AddressUI addressUI;
    private AddressService addressService;
    private Customer customer;
    String house_num;
    String street;
    String city;
    String province;
    String postal_code;

    public AddressController(AddressUI addressUI) {
        this.addressUI = addressUI;
        customer = Session.getInstance().getCustomer();

    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.addressService = new AddressService(conn);
    }

    private void initListeners() {

    }

    public boolean hasEmptyInput() {
        return (house_num.isEmpty() || street.isEmpty() || city.isEmpty() || province.isEmpty() || postal_code.isEmpty());
    }

    private class addressUpdateBtnListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (hasEmptyInput()) {
                JLabel addressErrorLabel = addressUI.getAddressErrorLbl();
                addressErrorLabel.setText("Error: empty inputs");
            } else {
                try {
                    ArrayList<Address> addresses = addressService.getAllCustomerAddresses(customer.getUsername());
                    

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                house_num = addressUI.getHouseField().getText();
                street = addressUI.getStreetField().getText();
                city = addressUI.getCityField().getText();
                province = addressUI.getProvinceField().getText();
                postal_code = addressUI.getProvinceField().getText();
            }
            //addressService.updateAddress(customer.getUsername(), );
            // close current frame and
        }
    }
}

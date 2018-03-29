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
    private Address currAddress;
    private String house_num;
    private String street;
    private String city;
    private String province;
    private String postal_code;
    JLabel addressErrorLabel;


    public AddressController(AddressUI addressUI) {
        this.addressUI = addressUI;
        addressErrorLabel = addressUI.getAddressErrorLbl();
        customer = Session.getInstance().getCustomer();
        initServices();

    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.addressService = new AddressService(conn);
    }

    public boolean hasEmptyInput() {
        return (house_num.isEmpty() || street.isEmpty() || city.isEmpty() || province.isEmpty() || postal_code.isEmpty());
    }


    public void addAddress() {
        getInputFields();

        if (hasEmptyInput()) {
            addressErrorLabel = addressUI.getAddressErrorLbl();
            addressErrorLabel.setText("Error: empty inputs");
        } else {
            try {
                int houseNum = Integer.parseInt(house_num);
                addressService.addCustomerAddress(customer.getUsername(), houseNum,
                        street, postal_code, city, province);

            } catch (SQLException e1) {
                addressErrorLabel.setText("Error: Could not add address");
            }
        }
    }

    public void updateAddress() {
        getInputFields();

        if (hasEmptyInput()) {
            addressErrorLabel = addressUI.getAddressErrorLbl();
            addressErrorLabel.setText("Error: empty inputs");
        } else {
            try {
                int houseNum = Integer.parseInt(house_num);
                addressService.updateAddress(customer.getUsername(), currAddress, houseNum,
                        street, postal_code, city, province);

            } catch (SQLException e1) {
                addressErrorLabel.setText("Error: Could not update address");
            }
        }
    }

    public void deleteAddress() {
        getInputFields();

        if (hasEmptyInput()) {
            addressErrorLabel = addressUI.getAddressErrorLbl();
            addressErrorLabel.setText("Error: empty inputs");
        } else {
            try {
                int houseNum = Integer.parseInt(house_num);
                addressService.deleteAddress(customer.getUsername(), houseNum,
                        street, postal_code);
            } catch (SQLException e1) {
                addressErrorLabel.setText("Error: Could not delete address");
            }
        }
    }

    public void getInputFields() {
        house_num = addressUI.getHouseField().getText();
        street = addressUI.getStreetField().getText();
        city = addressUI.getCityField().getText();
        province = addressUI.getProvinceField().getText();
        postal_code = addressUI.getPCField().getText();
    }
}
package client;

import model.*;
import service.AddressService;
import service.ConnectionService;
import service.ItemService;
import service.MysteryBoxService;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoxController extends FrameController {
    private MysteryBoxService mysteryBoxService;
    private AddressService addressService;
    private MainUI mainUI;
    private MysteryBox mysteryBox;
    private String house_num;
    private String street;
    private String city;
    private String province;
    private String postal_code;
    private Customer customer;

    public BoxController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
        customer = Session.getInstance().getCustomer();
    }

    public void setBoxPanel(String theme) {
        try {
            JLabel costLabel;
            if (theme.equals("Harry Potter")) {
                mysteryBox =  mysteryBoxService.getMysteryBox(5);
                costLabel = mainUI.getHpCostLbl();
            } else if (theme.equals("Anime")) {
                mysteryBox =  mysteryBoxService.getMysteryBox(7);
                costLabel = mainUI.getAnimeCost();
            } else {
                mysteryBox =  mysteryBoxService.getMysteryBox(8);
                costLabel = mainUI.getMarvelCostLbl();
            }

            costLabel.setText("$" + Double.toString(mysteryBox.getCost()) + "/month");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setShippintDetailsPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"House No.", "Street", "Postal Code", "City", "Province"};
        dtm.setColumnIdentifiers(header);
        mainUI.getAddresses().setModel(dtm);

        try {
            String username = Session.getInstance().getCustomer().getUsername();
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

    public boolean handleShipping() {
        if (getCurrentAddress() == null) {
            getInputFields();
            if (hasEmptyInput()) {
                createErrorDialog("Error: Please select an existing address or input a valid address");
                return false;
            } else {
                return addAddress();
            }
        }
        return false;
    }

    public void handlePayment() {
        if (!isCardSelected()) {

        }

    }

    private Address getCurrentAddress() {
        JTable addressTable = mainUI.getAddresses();
        int row = addressTable.getSelectedRow();

        if (row != -1) {
            ArrayList<String> data = new ArrayList<>();

            for (int i = 0; i < addressTable.getColumnCount(); i++) {
                data.add(addressTable.getModel().getValueAt(row, i).toString());
            }

            int house_num = Integer.parseInt(data.get(0));
            String street = data.get(1);
            String postal_code = data.get(2);
            String city = data.get(3);
            String province = data.get(4);

            return new Address(house_num, street, postal_code, city, province);
        } else {
            return null;
        }
    }

    private boolean isCardSelected() {
        JTable cardsTable = mainUI.getCreditCards();
        int row = cardsTable.getSelectedRow();

        return (row != -1);
    }

    public boolean addAddress() {
        int houseNum = Integer.parseInt(house_num);
        try {
            addressService.addCustomerAddress(customer.getUsername(), houseNum,
                    street, postal_code, city, province);
            return true;
        } catch (SQLException e) {
            createErrorDialog("Error: Could not add address to account");
            return false;
        }
    }

    public boolean hasEmptyInput() {
        return (house_num.isEmpty() || street.isEmpty() || city.isEmpty() || province.isEmpty() || postal_code.isEmpty());
    }

    public void getInputFields() {
        house_num = mainUI.getHouseNumField().getText();
        street = mainUI.getStreetField().getText();
        city = mainUI.getCityField().getText();
        province = mainUI.getProvinceField().getText();
        postal_code = mainUI.getPCField().getText();
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.mysteryBoxService = new MysteryBoxService(conn);
        this.addressService = new AddressService(conn);

    }
}
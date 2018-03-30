package client;

import model.*;
import service.*;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class BoxController extends FrameController {
    private MysteryBoxService mysteryBoxService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private SubscriptionService subscriptionService;
    private MainUI mainUI;
    private MysteryBox mysteryBox;
    private String house_num;
    private String street;
    private String city;
    private String province;
    private String postal_code;
    private String cardNo;
    private String expDate;
    private String cardType;
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

    public void setShippingDetailsPanel() {
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
            createErrorDialog("Error: could not retrieve addresses");
        }
    }

    public void setPaymentPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Card ID", "Expiry Date", "Type", "Last Digits"};
        dtm.setColumnIdentifiers(header);
        mainUI.getCreditCards().setModel(dtm);

        try {
            ArrayList<CreditCard> cards = creditCardService.getCustomerCreditCards(customer.getUsername());
            for (int i = 0; i < cards.size(); i++) {
                CreditCard card = cards.get(i);
                dtm.addRow(new Object[] {card.getCid(), card.getExpDate(), card.getType(), card.getLastDigits()});
            }

        } catch (SQLException e) {
            createErrorDialog("Error: could not retrieve credit cards");
        }
    }

    public boolean handleShipping() {
        if (getCurrentAddress() == null) {
            getAddressInputFields();
            if (hasEmptyAddressInput()) {
                createErrorDialog("Error: Please select an existing address or input a valid address");
                return false;
            } else {
                return addAddress();
            }
        }
        return true;
    }

    public void handlePayment(int mbid) {
        if (!isCardSelected()) {
            CreditCard card = getCreditCard();
            if (card != null) {
                try {
                    creditCardService.addCustomerCreditCard(customer.getUsername(), card);
                } catch (SQLException e) {
                    createErrorDialog("Error: Could not add the credit card to the account");
                }
            } else {
                createErrorDialog("Error: Please select an existing credit card or input a valid credit card");
            }
        }
        if (addSubscription(mbid)) {
            createErrorDialog("Subscription was added successfully! Enjoy.");
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

    private CreditCard getCreditCard() {
        getCreditCardInputFields();
        if (hasEmptyCardInput()) {
            return null;
        } else {
            int min = 15;
            int max = 500;
            int cid = ThreadLocalRandom.current().nextInt(min, max);
            try {
                Date date = stringToDate(expDate);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                String token = CardController.randomTokenGenerator();
                int lastDigits = getLastDigits();
                return new CreditCard(cid, sqlDate, token, cardType, lastDigits);
            } catch (ParseException e) {
                return null;
            }
        }
    }

    private boolean addSubscription(int mbid) {
        int min = 15;
        int max = 500;
        int sid = ThreadLocalRandom.current().nextInt(min, max);
        String status = "true";
        try {
            Date date = stringToDate("2018-04-06");
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            subscriptionService.addSubscription(sid, status, sqlDate, 1,
                    customer.getUsername(), mbid);
            return true;
        } catch (Exception e) {
            createErrorDialog("Error: Could not add subscription to account");
            return false;
        }
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

    public boolean hasEmptyAddressInput() {
        return (house_num.isEmpty() || street.isEmpty() || city.isEmpty() || province.isEmpty() || postal_code.isEmpty());
    }

    public boolean hasEmptyCardInput() {
        return (cardNo.isEmpty() || expDate.isEmpty() || cardType.isEmpty());
    }

    public void getCreditCardInputFields() {
        cardNo = mainUI.getCCNumber().getText();
        expDate = mainUI.getCCExpDate().getText();
        cardType = mainUI.getCCType().getText();
    }

    public void getAddressInputFields() {
        house_num = mainUI.getHouseNumField().getText();
        street = mainUI.getStreetField().getText();
        city = mainUI.getCityField().getText();
        province = mainUI.getProvinceField().getText();
        postal_code = mainUI.getPCField().getText();
    }

    public Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(dateStr);
    }

    public int getLastDigits() {
        if (cardNo.length() > 3) {
            String lastDigitsStr = cardNo.substring(cardNo.length() - 3);
            return Integer.parseInt(lastDigitsStr);
        } else {
            return -1;
        }
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.mysteryBoxService = new MysteryBoxService(conn);
        this.addressService = new AddressService(conn);
        this.creditCardService = new CreditCardService(conn);
        this.subscriptionService = new SubscriptionService(conn);

    }
}
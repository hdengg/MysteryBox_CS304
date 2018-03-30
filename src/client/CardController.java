package client;

import model.CreditCard;
import model.Customer;
import model.Session;
import service.ConnectionService;
import service.CreditCardService;
import ui.CardUI;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class CardController {
    private CreditCardService creditCardService;
    private MainUI mainUI;
    private Customer customer;
    private JTable creditCardTable;
    private JButton cardEditButton;
    private JTextField cardConsole;
    private CardUI cardUI;
    private int cid;
    private java.sql.Date expDate;
    private String token;
    private String type;
    private int lastDigits;

    public CardController(CardUI cardUI) {
        this.cardUI = cardUI;
        customer = Session.getInstance().getCustomer();
        cardConsole = cardUI.getCardConsole();
        initServices();
    }


    public void addCreditCard() {
        getInputFields();
        CreditCard creditCard = new CreditCard(cid, expDate, token, type, lastDigits);
        try {
            creditCardService.addCustomerCreditCard(customer.getUsername(), creditCard);
        } catch (SQLException e) {
            cardConsole.setText("Error: Could not add credit card");
        }
    }



    public void deleteCreditCard() {
        getInputFields();
        try {
            creditCardService.deleteCustomerCreditCard(customer.getUsername(), cid);
        } catch (SQLException e1) {
            cardConsole.setText("Error: Could not delete credit card");
        }
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.creditCardService = new CreditCardService(conn);
    }

    public static String randomTokenGenerator() {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        StringBuilder sb = new StringBuilder(6);
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    private void getInputFields() {
        cid = Integer.parseInt(cardUI.getCidField().getText());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String unParsedDate = cardUI.getExpiryField().getText();
        Date parsedDate = null;
        try {
            parsedDate = dateFormat.parse(unParsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        expDate = new java.sql.Date(parsedDate.getTime());
        token = randomTokenGenerator();
        type = cardUI.getTypeField().getText();
        lastDigits = Integer.parseInt(cardUI.getDigitsField().getText());
    }
}

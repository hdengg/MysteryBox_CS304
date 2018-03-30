package ui;

import client.CardController;
import model.CreditCard;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CardUI {
    private JPanel rootPanel;
    private JTextField cidField;
    private JTextField expiryField;
    private JTextField typeField;
    private JTextField digitsField;
    private JButton addCardBtn;
    private JButton deleteCardBtn;
    private JLabel cardErrorLbl;
    private JTextField cardConsole;
    private CardController cardController;

    public CardUI() {
        registerController();

        addCardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardController.addCreditCard();
                cardConsole.setText("Card added successfully");
            }
        });

        deleteCardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardController.deleteCreditCard();
                cardConsole.setText("Card deleted successfully");
            }
        });
    }

    public void registerController() {
        this.cardController = new CardController(this);
    }

    public void setCreditCardFields(CreditCard creditCard) {
        cidField.setText(Integer.toString(creditCard.getCid()));
        expiryField.setText(creditCard.getExpDate().toString());
        typeField.setText(creditCard.getType());
        digitsField.setText(Integer.toString(creditCard.getLastDigits()));
    }


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getCidField() {
        return cidField;
    }

    public JTextField getExpiryField() {
        return expiryField;
    }

    public JTextField getTypeField() {
        return typeField;
    }

    public JTextField getDigitsField() {
        return digitsField;
    }

    public JButton getAddCardBtn() {
        return addCardBtn;
    }

    public JButton getDeleteCardBtn() {
        return deleteCardBtn;
    }

    public JLabel getCardErrorLbl() {
        return cardErrorLbl;
    }

    public JTextField getCardConsole() {
        return cardConsole;
    }
}

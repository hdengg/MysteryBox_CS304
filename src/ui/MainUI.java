package ui;

import client.AccountController;
import client.BoxController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainUI {
    private JButton logoutButton;
    private JButton accountButton;
    private JButton subButton;
    private JPanel rootPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JComboBox mbButton;
    private JPanel AccountPanel;
    private JPanel SubscriptionPanel;
    private JPanel HPPanel;
    private JPanel ShippingPanel;
    private JLabel ATitle;
    private JLabel SBTitle;
    private JLabel HPTitle;
    private JPanel AnimePanel;
    private JPanel MarvelPanel;
    private JLabel AnimeTitle;
    private JLabel MarvelTitle;
    private JButton profileBtn;
    private JButton cardBtn;
    private JButton addressBtn;
    private JPanel profilePanel;
    private JLabel profileTitle;
    private JPanel profileContentPnl;
    private JLabel firstNameLbl;
    private JLabel lastNameLbl;
    private JLabel usernameLbl;
    private JLabel phoneLbl;
    private JLabel emailLbl;
    private JButton profileEditBtn;
    private JPanel AddressPanel;
    private JLabel addressTitle;
    private JTable addressTable;
    private JPanel custLeftPnl;
    private JButton animeSubBtn;
    private JLabel animeCost;
    private JTextArea textArea1;
    private JButton addressEditBtn;
    private JPanel adminLeftPnl;
    private JComboBox comboBox1;
    private JPanel CardPanel;
    private JLabel cardTitle;
    private JTable cardTable;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextField textField1;
    private JTextField textField2;
    private JPanel PaysWith;
    private JPanel ShippingDetails;
    private JButton payButton;
    private JButton ShipDetailsBtn;
    private JTable Addresses;
    private JTextField streetField;
    private JTextField PCField;
    private JTextField cityField;
    private JTextField provinceField;
    private JTextField CCNumber;
    private JTextField CCExpDate;
    private JTextField CCType;
    private JButton CCEditButton;

    private AccountController accountController;

    public MainUI() {
        registerController();

        subButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(SubscriptionPanel);
            }
        });
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(AccountPanel);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        mbButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedBox = (String) mbButton.getSelectedItem();
                if (selectedBox.equals("Harry Potter")) {
                    changePanel(HPPanel);
                } else if (selectedBox.equals("Anime")) {
                    changePanel(AnimePanel);
                } else if (selectedBox.equals("Marvel")) {
                    changePanel(MarvelPanel);
                }
            }
        });
        profileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(profilePanel);
                accountController.setProfilePanel();
            }
        });
        addressBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(AddressPanel);
                accountController.setAddressPanel();
            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void changePanel(JPanel panel) {
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();

        rightPanel.add(panel);
        rightPanel.repaint();
        rightPanel.revalidate();
    }

    private void createUIComponents() {
        String[] themes = {"Mystery Boxes", "Harry Potter", "Anime", "Marvel"};
        mbButton = new JComboBox(themes);
        mbButton.setSelectedIndex(0);
    }

    public void registerController() {
        accountController = new AccountController(this);
    }

    public JPanel getLeftPanel() { return leftPanel; }

    public JLabel getFirstNameLbl() { return firstNameLbl; }

    public JLabel getLastNameLbl() { return lastNameLbl; }

    public JLabel getUsernameLbl() { return usernameLbl;}

    public JLabel getPhoneLbl() { return phoneLbl; }

    public JLabel getEmailLbl() {return emailLbl; }

    public JTable getAddressTable() {return addressTable; }

    public JTable getCreditCardTable() {return cardTable; }

    public JButton getAddressEditBtn() { return addressEditBtn; }

    public JButton getCCEditButton() { return CCEditButton; }

    public JLabel getAnimeCost() { return animeCost; }
}

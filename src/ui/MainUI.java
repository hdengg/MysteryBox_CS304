package ui;

import client.*;
import model.Item;

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
    private JTextArea animeIsAmazingTextArea;
    private JButton addressEditBtn;
    private JPanel adminLeftPnl;
    private JPanel CardPanel;
    private JLabel cardTitle;
    private JTable cardTable;
    private JPanel PaysWithPanel;
    private JPanel ShippingDetailsPanel;
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
    private JTable CreditCards;
    private JButton cardEditBtn;
    private JLabel hpCostLbl;
    private JButton hpSubBtn;
    private JLabel marvelCostLbl;
    private JButton marvelSubBtn;
    private JButton CCEditButton;
    private JPanel adminBoxPanel;
    private JPanel adminItemsPanel;
    private JTable allBoxes;
    private JButton viewItemsButton;
    private JButton submitTotalQuery;
    private JTextField totalCostResult;
    private JButton minButton;
    private JTextField minMaxResult;
    private JButton maxButton;
    private JButton submitMostSubsQuery;
    private JTextField mostSubsResult;
    private JTable itemsTable;
    private JButton editItemButton;
    private JPanel adminSubsPanel;
    private JTable allSubscribers;
    private JButton addSubsButton;
    private JButton updateSubsButton;
    private JButton submitAllThemesQuery;
    private JTable allSubsThemeResult;
    private JTextField subTheme;
    private JButton submitNumSubThemeQuery;
    private JTextField numSubThemeResult;
    private JPanel adminCustomerPanel;
    private JTable adminCustomerTable;
    private JButton editButton1;
    private JButton AdminSubBtn;
    private JButton AdminCustBtn;
    private JButton AdminBoxesBtn;
    private JButton LogoutBtn1;
    private JPanel adminShipmentPanel;
    private JTable adminShipmentTable;
    private JTextField carrierField;
    private JButton submitSelectQuery;
    private JRadioButton subscriptionIDRadioButton;
    private JRadioButton shipDateRadioButton;
    private JRadioButton shipNo;
    private JRadioButton carrierRadioButton;
    private JRadioButton statusRadioButton;
    private JRadioButton trackingNoRadioButton;

    private AccountController accountController;
    private BoxController boxController;
    private SubscriptionsController subscriptionsController;
    private CustomerController customerController;
    private AdminBoxController adminBoxController;
    private ItemController itemController;

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
                boxController.setBoxPanel(selectedBox);
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
        addressEditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                accountController.createAddressEditWindow();
            }
        });
        cardBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(CardPanel);
                accountController.setCreditCardPanel();
            }
        });
        cardEditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        marvelSubBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        AdminSubBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(adminSubsPanel);
                subscriptionsController.initAdminSubscriptionPanel();
            }
        });
        AdminCustBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(adminCustomerPanel);
            }
        });
        AdminBoxesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel(adminBoxPanel);
            }
        });
        viewItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int mbid = adminBoxController.getSelectedBoxId();
                if (mbid != 0) {
                    changePanel(adminItemsPanel);
                    adminBoxController.setBoxID(mbid);
                    adminBoxController.initItemsPanel();
                }
            }
        });
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminBoxController.createItemEditWindow();

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
        boxController = new BoxController(this);
        subscriptionsController = new SubscriptionsController(this);
        customerController = new CustomerController(this);
        adminBoxController = new AdminBoxController(this);
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

    public JButton getCardEditBtn() { return cardEditBtn; }

    public JLabel getAnimeCost() { return animeCost; }

    public JLabel getHpCostLbl() { return hpCostLbl; }

    public JLabel getMarvelCostLbl() { return marvelCostLbl; }

    public JPanel getRightPanel() { return rightPanel; }

    public JPanel getAdminCustomerPanel() { return adminCustomerPanel; }

    public JTable getAllSubscribers() { return allSubscribers; }

    public JTable getAllSubsThemeResult() { return allSubsThemeResult; }

    public JTextField getSubThemeField() { return subTheme; }

    public JButton getSubButton() { return subButton; }

    public JButton getUpdateSubsButton() { return updateSubsButton; }

    public JButton getSubmitNumSubThemeQuery() { return submitNumSubThemeQuery; }

    public JButton getSubmitAllThemesQuery() { return submitAllThemesQuery; }

    public JTextField getNumSubThemeResult() { return numSubThemeResult; }

    public JButton getAddSubsButton() { return addSubsButton; }

    public JTable getAdminCustomerTable() { return adminCustomerTable;
    }

    public JButton getEditButton1() { return editButton1; }
  
    public JTable getAllBoxes() { return allBoxes; }

    public JButton getSubmitTotalQueryBtn() { return submitTotalQuery; }

    public JTextField getTotalCostResult() { return totalCostResult; }

    public JButton getMaxButton() { return maxButton; }

    public JButton getMinButton() { return minButton; }

    public JTextField getMinMaxResult() { return minMaxResult; }

    public JButton getSubmitMostSubsQuery() { return submitMostSubsQuery; }

    public JTextField getMostSubsResult() { return mostSubsResult; }

    public JTable getItemsTable() { return itemsTable; }

    public JButton getEditItemButton() { return editItemButton; }
}

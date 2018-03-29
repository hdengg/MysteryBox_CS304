package ui;

import client.AccountController;
import client.AddressController;
import service.AddressService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddressUI {
    private JPanel rootPanel;
    private JTextField houseField;
    private JTextField streetField;
    private JTextField PCField;
    private JTextField cityField;
    private JTextField provinceField;
    private JButton AddrAddBtn;
    private JButton AddrUpdateBtn;
    private JButton AddrDeleteBtn;
    private JPanel addressErrorPnl;
    private JLabel addressErrorLbl;

    private AddressController addressController;

    public AddressUI() {
        registerController();

        AddrAddBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        AddrUpdateBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        AddrDeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JTextField getHouseField() { return houseField; }

    public JTextField getStreetField() { return streetField; }

    public JTextField getPCField() { return PCField; }

    public JTextField getCityField() { return cityField; }

    public JTextField getProvinceField() { return provinceField; }

    public JPanel getRootPanel() { return rootPanel; }

    public JButton getAddrUpdateBtn() { return AddrUpdateBtn; }

    public JButton getAddrDeleteBtn() { return AddrDeleteBtn; }

    public JPanel getAddressErrorPnl() { return addressErrorPnl; }

    public JLabel getAddressErrorLbl() { return addressErrorLbl; }

    public void registerController() {
        this.addressController = new AddressController(this);
    }
}



package ui;

import client.ItemController;

import javax.swing.*;

public class ItemUI {
    private JPanel rootPanel;
    private JTextField idField;
    private JTextField valueField;
    private JTextField nameField;
    private JPanel itemErrorPnl;



    private JLabel itemErrorLbl;
    private JButton ItemAddBtn;
    private JButton ItemUpdateBtn;
    private JButton ItemDeleteBtn;

    private ItemController itemController;

    public ItemUI(int mbid) {
        registerController();
        itemController.setBoxID(mbid);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getIdField() {
        return idField;
    }

    public JTextField getValueField() {
        return valueField;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JPanel getItemErrorPnl() {
        return itemErrorPnl;
    }

    public JLabel getItemErrorLbl() {
        return itemErrorLbl;
    }
    public JButton getItemAddBtn() {
        return ItemAddBtn;
    }

    public JButton getItemUpdateBtn() {
        return ItemUpdateBtn;
    }

    public JButton getItemDeleteBtn() {
        return ItemDeleteBtn;
    }

    private void registerController() {
        itemController = new ItemController(this);
    }
}

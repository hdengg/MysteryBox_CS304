package ui;

import javax.swing.*;

public class ItemUI {
    private JPanel rootPanel;
    private JTextField idField;
    private JTextField valueField;
    private JTextField nameField;
    private JPanel itemErrorPnl;
    private JButton ItemAddBtn;
    private JButton ItemUpdateBtn;
    private JButton ItemDeleteBtn;

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

    public JButton getItemAddBtn() {
        return ItemAddBtn;
    }

    public JButton getItemUpdateBtn() {
        return ItemUpdateBtn;
    }

    public JButton getItemDeleteBtn() {
        return ItemDeleteBtn;
    }
}

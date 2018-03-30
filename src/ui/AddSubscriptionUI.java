package ui;

import client.AddSubscriptionController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddSubscriptionUI {
    private JTextField usernameField;
    private JTextField mbidField;
    private JButton addSubBtn;
    private JLabel errorLabel;
    private JLabel usernameLbl;
    private JLabel mbid;
    private JPanel rootPanel;

    private AddSubscriptionController addSubscriptionController;

    public AddSubscriptionUI() {
        registerController();
        addSubBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSubscriptionController.addSubscription();
            }
        });
    }

    public JLabel getErrorLabel() { return errorLabel; }

    public JLabel getMbid() { return mbid; }

    public JPanel getRootPanel() { return rootPanel; }

    public JTextField getUsernameField() { return usernameField; }

    public JTextField getMbidField() { return mbidField; }

    private void registerController() {
        addSubscriptionController = new AddSubscriptionController(this);
    }
}

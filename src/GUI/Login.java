package GUI;

import client.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Login {
    private JPanel leftPanel;
    private JPanel rootPanel;
    private JPanel rightPanel;
    private JTextField userField;
    private JTextField passwordField;
    private JButton loginButton;
    private JLabel MainTitle;
    private JPanel rightContentPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel iconLabel;


    private void createUIComponents() {
        usernameLabel = new JLabel("Username");
        usernameLabel.setBorder(new EmptyBorder(15, 15, 15, 15));

        passwordLabel = new JLabel("Password");
        passwordLabel.setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JTextField getUserField() {
        return userField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }
}

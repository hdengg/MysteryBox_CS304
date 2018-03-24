package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel leftPanel;
    public JPanel rootPanel;
    private JPanel rightPanel;
    private JTextField userText;
    private JTextField passwordText;
    private JButton loginButton;
    private JLabel MainTitle;
    private JPanel rightContentPanel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel iconLabel;


    public Login() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private void createUIComponents() {
        System.out.println();
        iconLabel = new JLabel(new ImageIcon("./assets/mysterybox_icon.png"));
    }
}

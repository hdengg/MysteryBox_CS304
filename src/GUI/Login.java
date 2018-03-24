package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

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
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current relative path is: " + s);
//        iconLabel = new JLabel(new ImageIcon("/MysteryBox_CS304/assets/mysterybox_icon.png"));
    }
}

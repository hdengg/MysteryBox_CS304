import GUI.Login;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame loginFrame = new JFrame("User Login");
        loginFrame.setContentPane(new Login().rootPanel);
        loginFrame.setVisible(true);
        //DBConnection connection = new DBConnection();
    }
}
package client;

import GUI.Login;
import model.Session;
import service.AddressService;
import service.CustomerService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController {
    private JFrame loginFrame;
    private Login loginUI;
    private JButton loginButton;
    private int MAX_LOGIN_ATTEMPTS = 3;
    private int loginAttempts;
    private CustomerService customerService;
    private AddressService addressService;
    private Session session;

    public MainFrameController(Session session, CustomerService customerService, AddressService addressService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.session = session;
        initLoginWindow();
        initListeners();
    }

    public void initLoginWindow() {
        loginFrame = new JFrame("User Login");
        loginUI = new Login();
        loginButton = loginUI.getLoginButton();
        loginFrame.setContentPane(loginUI.getRootPanel());
        // size the window to obtain a best fit for the components
        loginFrame.pack();

        // center the frame
        Dimension d = loginFrame.getToolkit().getScreenSize();
        Rectangle r = loginFrame.getBounds();
        loginFrame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        loginFrame.setVisible(true);

        // anonymous inner class for closing the window
        loginFrame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }

    private void initListeners() {
        loginButton.addActionListener(new loginBtnListner());
    }

    private class loginBtnListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = loginUI.getUserField().getText();
            String password = loginUI.getPasswordField().getText();
            if (customerService.login(session, username, password)) {
                //TODO: open main app frame
            } else {
                loginAttempts++;
                if (loginAttempts >= MAX_LOGIN_ATTEMPTS) {
                    loginFrame.dispose();
                    System.exit(-1);
                } else {
                    loginUI.getPasswordField().setText("");
                }
            }
        }
    }
}

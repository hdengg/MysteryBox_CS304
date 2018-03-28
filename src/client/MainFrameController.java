package client;

import ui.Login;
import model.Session;
import service.AddressService;
import service.CreditCardService;
import service.CustomerService;
import ui.CustomerUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController {
    private JFrame loginFrame;
    private JFrame customerFrame;
    private Login loginUI;
    private JButton loginButton;
    private int MAX_LOGIN_ATTEMPTS = 3;
    private int loginAttempts;
    private CustomerService customerService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private Session session;

    public MainFrameController(Session session, CustomerService customerService, AddressService addressService,
                               CreditCardService creditCardService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.creditCardService = creditCardService;
        this.session = session;
        initLoginWindow();
        initListeners();
    }

    public void initLoginWindow() {
        loginFrame = new JFrame("User Login");
        loginUI = new Login();
        loginButton = loginUI.getLoginButton();
        loginFrame.setContentPane(loginUI.getRootPanel());
        setFrameProperties(loginFrame);
//        customerFrame = new JFrame("MysteryBox Customer Application");
//        CustomerUI customerUI = new CustomerUI();
//        customerFrame.setContentPane(customerUI.getRootPanel());
//        setFrameProperties(customerFrame);
    }

    private void initListeners() {
        loginButton.addActionListener(new loginBtnListner());
    }

    private class loginBtnListner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = loginUI.getUserField().getText();
            String password = loginUI.getPasswordField().getText();
            if (customerService.login(session, username, password)) {
                loginFrame.dispose();
                customerFrame = new JFrame("MysteryBox Customer Application");
                CustomerUI customerUI = new CustomerUI();
                AccountController accountController = new AccountController(customerService, addressService, creditCardService, customerUI);
                customerFrame.setContentPane(customerUI.getRootPanel());
                setFrameProperties(customerFrame);
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

    private void setFrameProperties(JFrame frame) {
        // size the window to obtain a best fit for the components
        frame.pack();

        // center the frame
        Dimension d = frame.getToolkit().getScreenSize();
        Rectangle r = frame.getBounds();
        frame.setLocation( (d.width - r.width)/2, (d.height - r.height)/2 );
        frame.setVisible(true);

        // anonymous inner class for closing the window
        frame.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }
        });
    }
}

package client;

import ui.Login;
import model.Session;
import service.AddressService;
import service.CreditCardService;
import service.CustomerService;
import ui.MainUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrameController extends FrameController {
    private JFrame loginFrame;
    private JFrame mainFrame;
    private Login loginUI;
    private JButton loginButton;
    private int MAX_LOGIN_ATTEMPTS = 3;
    private int loginAttempts;
    private CustomerService customerService;
    private AddressService addressService;
    private CreditCardService creditCardService;
    private Session session;

    public MainFrameController (Session session, CustomerService customerService, AddressService addressService,
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
//        mainFrame = new JFrame("MysteryBox Customer Application");
//        MainUI mainUI = getView(false);
//        // AccountController accountController = new AccountController(customerService, addressService, creditCardService, mainUI);
//        mainFrame.setContentPane(mainUI.getRootPanel());
//        setFrameProperties(mainFrame);
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
                mainFrame = new JFrame("MysteryBox Application");
                MainUI mainUI = getView(Session.getInstance().isAdmin());
                mainFrame.setContentPane(mainUI.getRootPanel());
                setFrameProperties(mainFrame);
                addCloseHandling(mainFrame);
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

    private MainUI getView(boolean isAdmin) {
        MainUI mainUI = new MainUI();
        if (isAdmin) {
            JPanel leftPanel = mainUI.getLeftPanel();
            CardLayout leftCardLayout = (CardLayout) leftPanel.getLayout();
            leftCardLayout.next(leftPanel);
            JPanel rightPanel = mainUI.getRightPanel();
            CardLayout rightCardLayout = (CardLayout) rightPanel.getLayout();
            rightPanel.add(mainUI.getAdminCustomerPanel(), "adminCustomerPanel");
            rightCardLayout.show(rightPanel, "adminCustomerPanel");
        }

        return mainUI;
    }

    private void addCloseHandling(JFrame frame) {
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

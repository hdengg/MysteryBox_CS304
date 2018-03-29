package ui;

import service.ConnectionService;
import service.SubscriptionService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateSubscriptionUI {
    private JTextField sidField;
    private JTextField statusField;
    private JTextField fromField;
    private JTextField numMonthsField;
    private JTextField usernameField;
    private JButton updateSubBtn;
    private JLabel errorLabel;
    private JPanel rootPanel;

    private SubscriptionService subscriptionService;

    public UpdateSubscriptionUI() {
        initServices();

        updateSubBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    int sid = Integer.parseInt(sidField.getText());
                    boolean status = Boolean.parseBoolean(statusField.getText());
                    int num_months = Integer.parseInt(numMonthsField.getText());
                    String username = usernameField.getText();
                    Date date = stringToDate(fromField.getText());
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                    subscriptionService.updateSubscription(sid, status, sqlDate, num_months, username);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    errorLabel.setText("Error: update subscription failed");
                }
            }
        });
    }

    public Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        return formatter.parse(dateStr);

    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        subscriptionService = new SubscriptionService(conn);
    }

    public JPanel getRootPanel() { return rootPanel; }
}

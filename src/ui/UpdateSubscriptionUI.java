package ui;

import client.SubscriptionsController;
import service.ConnectionService;
import service.SubscriptionService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class UpdateSubscriptionUI {
    private JTextField sidField;
    private JTextField statusField;
    private JTextField fromField;
    private JTextField numMonthsField;
    private JTextField usernameField;
    private JButton updateSubBtn;
    private JLabel errorLabel;

    private SubscriptionService subscriptionService;

    public UpdateSubscriptionUI() {
        initServices();

        updateSubBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sid = Integer.parseInt(sidField.getText());

                //subscriptionService.updateSubscription();

            }
        });
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        subscriptionService = new SubscriptionService(conn);
    }


}

package client;

import model.Subscription;
import service.ConnectionService;
import service.SubscriptionService;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SubscriptionsController {
    private SubscriptionService subscriptionService;
    private MainUI mainUI;
    private JTable allSubscribers;
    private JButton submitNumSubs;
    private JButton submitSubAllThemes;
    private JButton addSubscriptionBtn;

    public SubscriptionsController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
        initAdminSubscriptionPanel();
        initActionListeners();

    }

    public void initAdminSubscriptionPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Subscription ID", "Status", "From", "Num Months"};
        dtm.setColumnIdentifiers(header);
        allSubscribers = mainUI.getAllSubscribers();
        allSubscribers.setModel(dtm);

        try {
            List<Subscription> subscriptions = subscriptionService.getSubscriptions();
            for (int i = 0; i < subscriptions.size(); i++) {
                Subscription subscription = subscriptions.get(i);
                dtm.addRow(new Object[] {subscription.getSid(), subscription.getStatus(),
                subscription.getFrom(), subscription.getNum_months()});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initActionListeners() {
        submitNumSubs = mainUI.getSubmitNumSubThemeQuery();
        submitNumSubs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String theme = mainUI.getSubThemeField().getText();
                int num = getNumSubsFromTheme(theme);
                mainUI.getNumSubThemeResult().setText(String.valueOf(num));
            }
        });
        submitSubAllThemes = mainUI.getSubmitAllThemesQuery();
        submitSubAllThemes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getUsersSubToAllThemes();
            }
        });

        addSubscriptionBtn = mainUI.getAddSubsButton();
        addSubscriptionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private int getNumSubsFromTheme(String theme) {
        int numSubs = 0;
        try {
            numSubs =  subscriptionService.getNumSubsFromTheme(theme);
            return subscriptionService.getNumSubsFromTheme(theme);
        } catch (SQLException e) {
            e.printStackTrace();
            //TODO: error dialog "Could not retrieve results"
        }
        return numSubs;
    }

    private void getUsersSubToAllThemes() {
        JTable subToAllThemesTable = mainUI.getAllSubsThemeResult();
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Username"};
        dtm.setColumnIdentifiers(header);
        subToAllThemesTable.setModel(dtm);

        try {
            List<String> usernames = subscriptionService.getUsersSubscribedToAllThemes();
            for (int i = 0; i < usernames.size(); i++) {
                String username = usernames.get(i);
                dtm.addRow(new Object[] {username});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.subscriptionService = new SubscriptionService(conn);
    }
}

package client;

import model.MysteryBox;
import model.Subscription;
import service.ConnectionService;
import service.MysteryBoxService;
import service.SubscriptionService;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class AdminBoxController {
    private MainUI mainUI;
    private MysteryBoxService mysteryBoxService;
    private SubscriptionService subscriptionService;
    private JTable allBoxesTable;
    private JButton submitTotalCostBtn;
    private JButton maxBtn;
    private JButton minBtn;
    private JButton mostSubsBtn;

    public AdminBoxController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
        initAdminBoxesPanel();
        initActionListeners();

    }

    private void initAdminBoxesPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"mbid", "no_items", "mdate", "theme", "cost"};
        dtm.setColumnIdentifiers(header);
        allBoxesTable = mainUI.getAllBoxes();
        allBoxesTable.setModel(dtm);

        try {
            List<MysteryBox> mysteryBoxes = mysteryBoxService.getAllMysteryBoxes();
            for (int i = 0; i < mysteryBoxes.size(); i++) {
                MysteryBox mysteryBox = mysteryBoxes.get(i);
                dtm.addRow(new Object[] {mysteryBox.getMbid(), mysteryBox.getNo_items(),
                mysteryBox.getMdate(), mysteryBox.getTheme(), mysteryBox.getCost()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void initActionListeners() {
        submitTotalCostBtn = mainUI.getSubmitTotalQueryBtn();
        submitTotalCostBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                float revenue = getCostOfAllSubscribedBoxes();
                mainUI.getTotalCostResult().setText(String.valueOf(revenue));
            }
        });
        maxBtn = mainUI.getMaxButton();
        maxBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashMap<Integer, Double> result = mysteryBoxService.getAverage(true);
                    for (Integer name: result.keySet()){

                        String key = name.toString();
                        String value = result.get(name).toString();
                        mainUI.getMinMaxResult().setText("mbid " +  key + ": " + value);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        minBtn = mainUI.getMinButton();
        minBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashMap<Integer, Double> result = mysteryBoxService.getAverage(false);
                    for (Integer name: result.keySet()){

                        String key = name.toString();
                        String value = result.get(name).toString();
                        mainUI.getMinMaxResult().setText("mbid " +  key + ": " + value);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        mostSubsBtn = mainUI.getSubmitMostSubsQuery();
        mostSubsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HashMap<String, Integer> result = subscriptionService.getThemeWithMostSubs();
                    for (String name: result.keySet()){

                        String key = name.toString();
                        String value = result.get(name).toString();
                        mainUI.getMostSubsResult().setText("theme: " +  key + ", subscriptions: " + value);
                    }

                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    private float getCostOfAllSubscribedBoxes() {
        float revenue = 0;
        try {
            revenue = mysteryBoxService.getRevenue();
        } catch (SQLException e) {
            e.printStackTrace(); //TODO: add error dialog
        }

        return revenue;
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.mysteryBoxService = new MysteryBoxService(conn);
        this.subscriptionService = new SubscriptionService(conn);
    }
}

package client;

import model.Item;
import model.MysteryBox;
import model.Subscription;
import service.ConnectionService;
import service.ItemService;
import service.MysteryBoxService;
import service.SubscriptionService;
import ui.ErrorDialog;
import ui.ItemUI;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminBoxController extends FrameController {
    private MainUI mainUI;
    private MysteryBoxService mysteryBoxService;
    private SubscriptionService subscriptionService;
    private ItemService itemService;
    private JTable allBoxesTable;
    private JButton submitTotalCostBtn;
    private JButton maxBtn;
    private JButton minBtn;
    private JButton mostSubsBtn;
    private JTable allItemsTable;
    private int mbid;
    private JButton editItemButton;

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
        editItemButton = mainUI.getEditItemButton();
        editItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    private float getCostOfAllSubscribedBoxes() {
        float revenue = 0;
        try {
            revenue = mysteryBoxService.getRevenue();
        } catch (SQLException e) {
            createErrorDialog("Error: Could not compute.");
        }
        return revenue;
    }

    public int getSelectedBoxId() {
        int row = allBoxesTable.getSelectedRow();
        if (row != -1) {
            return (int) allBoxesTable.getModel().getValueAt(row, 0);
        } else {
            createErrorDialog("Error: Please select a row");
            return 0;
        }
    }

    public void initItemsPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Item ID", "Value", "Name"};
        dtm.setColumnIdentifiers(header);
        allItemsTable = mainUI.getItemsTable();
        allItemsTable.setModel(dtm);

        try {
            List<Item> items = itemService.getItemsFromBox(mbid);
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                dtm.addRow(new Object[] {item.getItem_id(), item.getValue(), item.getItem_name()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBoxID(int mbid) {
        this.mbid = mbid;
    }

    public void createItemEditWindow() {
        JFrame editItemFrame = new JFrame("Edit Item");
        ItemUI itemUI = new ItemUI(mbid);

        editItemFrame.setContentPane(itemUI.getRootPanel());
        setFrameProperties(editItemFrame);
        editItemFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }


    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.mysteryBoxService = new MysteryBoxService(conn);
        this.subscriptionService = new SubscriptionService(conn);
        this.itemService = new ItemService(conn);

    }
}

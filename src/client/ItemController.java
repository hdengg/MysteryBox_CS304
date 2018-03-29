package client;

import service.ConnectionService;
import service.ItemService;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;

public class ItemController {
    private MainUI mainUI;
    private ItemService itemService;
    private JTable allItemsTable;

    public ItemController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
    }

    private void initItemsPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Item ID", "value", "mdate", "theme", "cost"};
        dtm.setColumnIdentifiers(header);
        allItemsTable = mainUI.getItemsTable();
        allItemsTable.setModel(dtm);

    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.itemService = new ItemService(conn);
    }

}

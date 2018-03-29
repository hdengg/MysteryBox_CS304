package client;

import model.Item;
import service.ConnectionService;
import service.ItemService;
import ui.ItemUI;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemController extends FrameController {
    private ItemUI itemUI;
    private ItemService itemService;
    private JTable allItemsTable;
    private int mbid;
    private JButton editItemButton;

    public ItemController(ItemUI itemUI) {
        this.itemUI = itemUI;
        initServices();
        //initActionListners();
    }

    public void setBoxID(int mbid) {
        this.mbid = mbid;
    }

//    public void initActionListners() {
//        editItemButton = mainUI.getEditItemButton();
//        editItemButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                createItemEditWindow();
//            }
//        });
//    }

    public void deleteItem() {

        // itemService.deleteItem();

    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.itemService = new ItemService(conn);
    }

}

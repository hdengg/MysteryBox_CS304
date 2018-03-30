package client;

import model.Item;
import model.MysteryBox;
import service.ConnectionService;
import service.ItemService;
import service.MysteryBoxService;
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
    private MysteryBoxService mysteryBoxService;
    private JTable allItemsTable;
    private int mbid;
    private JButton updatetItemButton;
    private JButton deleteItemButton;
    private JButton addItemButton;

    public ItemController(ItemUI itemUI) {
        this.itemUI = itemUI;
        initServices();
        initActionListners();
    }

    public void setBoxID(int mbid) {
        this.mbid = mbid;
    }

    public void initActionListners() {
        updatetItemButton = itemUI.getItemUpdateBtn();
        updatetItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateItem();
            }
        });
        addItemButton = itemUI.getItemAddBtn();
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem();
            }
        });
        deleteItemButton = itemUI.getItemDeleteBtn();
        deleteItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
    }

    public void addItem() {
        //TODO: use services to add item to DB
        System.out.println("add item");

    }

    public void updateItem() {
        //TODO: use services to update item to DB
        System.out.println("update item");
    }

    public void deleteItem() {
        // itemService.deleteItem();
        //TODO: use services to delete item to DB
        System.out.println("delete item");
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.itemService = new ItemService(conn);
        this.mysteryBoxService = new MysteryBoxService(conn);
    }

}

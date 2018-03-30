package client;

import service.ConnectionService;
import service.ItemService;
import service.MysteryBoxService;
import ui.ItemUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

public class ItemController extends FrameController {
    private ItemUI itemUI;
    private ItemService itemService;
    private MysteryBoxService mysteryBoxService;
    private JTable allItemsTable;
    private int mbid;
    private JButton updatetItemButton;
    private JButton deleteItemButton;
    private JButton addItemButton;
    private String item_id;
    private String item_value;
    private String item_name;
    JLabel itemErrorLabel;

    public ItemController(ItemUI itemUI) {
        this.itemUI = itemUI;
        initServices();
        initActionListners();
        itemErrorLabel = itemUI.getItemErrorLbl();
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

    public boolean hasEmptyInput() {
        return (item_id.isEmpty() || item_value.isEmpty() || item_name.isEmpty() );
    }

    public void addItem() {
        getInputFields();
        itemErrorLabel = itemUI.getItemErrorLbl();

        if (hasEmptyInput()) {
            itemErrorLabel.setText("Error: empty inputs");
        } else {
            try {
                int itemID = Integer.parseInt(item_id);
                double value = Double.parseDouble(item_value);
                itemService.addItem(itemID, value, item_name);
                mysteryBoxService.addItemToBox(mbid, itemID);
                itemErrorLabel.setText("Added item successfully");

            } catch (SQLException e1) {
                itemErrorLabel.setText("Error: Could not add item");
            }
        }
    }

    public void updateItem() {
        getInputFields();
        itemErrorLabel = itemUI.getItemErrorLbl();

        if (hasEmptyInput()) {
            itemErrorLabel.setText("Error: empty inputs");
        } else {
            try {
                int itemID = Integer.parseInt(item_id);
                double value = Double.parseDouble(item_value);
                itemService.updateItem(itemID, value, item_name);
                itemErrorLabel.setText("Updated item successfully");

            } catch (SQLException e1) {
                itemErrorLabel.setText("Error: could not update item");
            }
        }
    }

    public void deleteItem() {
        getInputFields();
        itemErrorLabel = itemUI.getItemErrorLbl();

        if (hasEmptyInput()) {
            itemErrorLabel.setText("Error: empty inputs");
        } else {
            try {
                int itemID = Integer.parseInt(item_id);
                itemService.deleteItem(itemID);
                itemErrorLabel.setText("Deleted item successfully");

            } catch (SQLException e1) {
                itemErrorLabel.setText("Error: could not delete address");
            }
        }

    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.itemService = new ItemService(conn);
        this.mysteryBoxService = new MysteryBoxService(conn);
    }

    public void getInputFields() {
        item_id = itemUI.getIdField().getText();
        item_value = itemUI.getValueField().getText();
        item_name = itemUI.getNameField().getText();
    }

}

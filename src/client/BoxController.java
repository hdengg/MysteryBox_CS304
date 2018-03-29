package client;

import model.MysteryBox;
import service.ConnectionService;
import service.ItemService;
import service.MysteryBoxService;
import ui.MainUI;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class BoxController {
    private MysteryBoxService mysteryBoxService;
    private ItemService itemService;
    private MainUI mainUI;
    private MysteryBox mysteryBox;

    public BoxController(MainUI mainUI) {
        this.mainUI = mainUI;
        initServices();
    }

    public void setBoxPanel(String theme) {
        try {
            JLabel costLabel;
            if (theme.equals("Harry Potter")) {
                mysteryBox =  mysteryBoxService.getMysteryBox(5);
                costLabel = mainUI.getHpCostLbl();
            } else if (theme.equals("Anime")) {
                mysteryBox =  mysteryBoxService.getMysteryBox(7);
                costLabel = mainUI.getAnimeCost();
            } else {
                mysteryBox =  mysteryBoxService.getMysteryBox(8);
                costLabel = mainUI.getMarvelCostLbl();
            }

            costLabel.setText("$" + Double.toString(mysteryBox.getCost()) + "/month");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.mysteryBoxService = new MysteryBoxService(conn);
        this.itemService = new ItemService(conn);
    }
}
package client;

import model.MysteryBox;
import service.ItemService;
import service.MysteryBoxService;
import ui.MainUI;

public class BoxController {
    private MysteryBoxService mysteryBoxService;
    private ItemService itemService;
    private MainUI mainUI;

    public BoxController(MysteryBoxService mbService, ItemService itemService, MainUI mainUI) {
        this.mysteryBoxService = mbService;
        this.itemService = itemService;
        this.mainUI = mainUI;
    }

    public void setBoxPanel() {

        MysteryBox mb = mysteryBoxService.getMysteryBox(7);



    }
}

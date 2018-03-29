package ui;

import javax.swing.*;

public class MysteryBoxUI {
    private JPanel rootPanel;
    private JTextField mbid;
    private JTextField no_items;
    private JTextField mdate;
    private JTextField theme;
    private JTextField cost;
    private JPanel mysteryErrorPnl;
    private JButton MBoxAddBtn;
    private JButton MboxUpdateBtn;
    private JButton MboxDeleteBtn;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getMbid() {
        return mbid;
    }

    public JTextField getNo_items() {
        return no_items;
    }

    public JTextField getMdate() {
        return mdate;
    }

    public JTextField getTheme() {
        return theme;
    }

    public JTextField getCost() {
        return cost;
    }

    public JPanel getMysteryErrorPnl() {
        return mysteryErrorPnl;
    }

    public JButton getMBoxAddBtn() {
        return MBoxAddBtn;
    }

    public JButton getMboxUpdateBtn() {
        return MboxUpdateBtn;
    }

    public JButton getMboxDeleteBtn() {
        return MboxDeleteBtn;
    }
}

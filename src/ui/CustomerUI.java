package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CustomerUI {
    private JButton logoutButton;
    private JButton accountButton;
    private JButton subButton;
    private JPanel rootPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JComboBox mbButton;
    private JPanel AccountPanel;
    private JPanel SubscriptionPanel;
    private JPanel BoxPanel;
    private JPanel ShippingPanel;
    private JLabel ATitle;
    private JLabel SBTitle;

    public CustomerUI() {
        subButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePanels();

                rightPanel.add(SubscriptionPanel);
                rightPanel.repaint();
                rightPanel.revalidate();
            }
        });
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePanels();

                rightPanel.add(AccountPanel);
                rightPanel.repaint();
                rightPanel.revalidate();

            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    private void removePanels() {
        rightPanel.removeAll();
        rightPanel.repaint();
        rightPanel.revalidate();
    }

}

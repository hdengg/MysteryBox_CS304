package client;

import model.Shipment;
import service.ConnectionService;
import service.ShipmentService;
import ui.MainUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShipmentController extends FrameController {
    private ShipmentService shipmentService;
    private MainUI mainUI;
    private JTable adminShipmentTable;
    private JButton submitSelectQuery;
    private boolean isShipNoSelected;
    private boolean isCarrierSelected;
    private boolean isStatusSelected;
    private boolean isSubscriptionSelected;
    private boolean isTrackingSelected;
    private boolean isShipDateSelected;
    private JTextField carrierField;
    private JTextField shipmentErrLbl;
    private int shipping_no;
    private String carrier;
    private String status;
    private Date ship_date;
    private String tracking_no;
    private int s_id;

    public ShipmentController(MainUI mainUI) {
        this.mainUI = mainUI;
        shipmentErrLbl = mainUI.getShipmentErrorLbl();
        initServices();
        initAdminShipmentPanel();
        initActionListeners();

    }

    private void initAdminShipmentPanel() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Shipping No", "Carrier", "Status", "Ship Date", "Tracking No", "Subscriber ID"};
        dtm.setColumnIdentifiers(header);
        }

    private void initActionListeners() {
        submitSelectQuery = mainUI.getSubmitSelectQuery();
        submitSelectQuery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carrier = mainUI.getCarrierField().getText();
                System.out.println(carrier);
                if (!carrier.equals("Fedex") && !carrier.equals("Canada Post") && !carrier.equals("UPS")) {
                    shipmentErrLbl = mainUI.getShipmentErrorLbl();
                    shipmentErrLbl.setText("Error: Invalid Carrier. Supported Carriers are: Canada Post, UPS, and"
                            + " FEDEX");
                }
                setBooleanFields();
                if (!(isShipNoSelected || isCarrierSelected || isStatusSelected || isShipDateSelected
                        || isSubscriptionSelected || isTrackingSelected)) {
                    showAllShipments();
                } else {
                    showSelectShipments();
                }
            }
        });
    }

    private void setBooleanFields() {
        isShipNoSelected = mainUI.getShipNo().isSelected();
        isCarrierSelected = mainUI.getCarrierRadioButton().isSelected();
        isStatusSelected = mainUI.getStatusRadioButton().isSelected();
        isShipDateSelected = mainUI.getShipDateRadioButton().isSelected();
        isSubscriptionSelected = mainUI.getSubscriptionIDRadioButton().isSelected();
        isTrackingSelected = mainUI.getTrackingNoRadioButton().isSelected();
    }

    private void showAllShipments() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Shipping No", "Carrier", "Status", "Ship Date", "Tracking No", "Subscriber ID"};
        dtm.setColumnIdentifiers(header);
        mainUI.getAdminShipmentTable().setModel(dtm);

        try {
            ArrayList<Shipment> shipments = shipmentService.getSelectShipments(carrier,
                    true, true, true, true, true,
                    true);
            for (int i = 0; i < shipments.size(); i++) {
                Shipment shipment = shipments.get(i);
                dtm.addRow(new Object[] {shipment.getShipping_no(), shipment.getCarrier(), shipment.getStatus(),
                        shipment.getShip_date(), shipment.getTracking_no(), shipment.getSid()});
            }

        } catch (SQLException e) {
            shipmentErrLbl.setText("Error: There was an error processing your query. Please try again");
        }
    }

    private void showSelectShipments() {
        DefaultTableModel dtm = new DefaultTableModel(0, 0);
        String[] header = new String[] {"Shipping No", "Carrier", "Status", "Ship Date", "Tracking No", "Subscriber ID"};
        dtm.setColumnIdentifiers(header);
        mainUI.getAdminShipmentTable().setModel(dtm);

        try {
            ArrayList<Shipment> shipments = shipmentService.getSelectShipments(carrier,
                    isShipNoSelected, isCarrierSelected, isStatusSelected, isShipDateSelected, isTrackingSelected,
                    isSubscriptionSelected);
            for (int i = 0; i < shipments.size(); i++) {
                Shipment shipment = shipments.get(i);
                Integer ship = shipment.getShipping_no();
                Integer sid = shipment.getSid();
                if (ship == -1) { ship = null; }
                if (sid == -1 ) { sid = null; }

                dtm.addRow(new Object[]{ship, shipment.getCarrier(), shipment.getStatus(),
                        shipment.getShip_date(), shipment.getTracking_no(), sid});
            }

        } catch (SQLException e) {
            shipmentErrLbl.setText("Error: There was an error processing your query. Please try again");
        }
    }


    private void initServices() {
        Connection conn = ConnectionService.getInstance().getConnection();
        this.shipmentService = new ShipmentService(conn);
    }
}

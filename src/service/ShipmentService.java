package service;

import model.Shipment;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by janicelee on 2018-03-24.
 */
public class ShipmentService {
    private Connection connection;

    public ShipmentService(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Shipment> getShipments(int subscriptionId) throws SQLException {
        ArrayList<Shipment> shipments = new ArrayList();

        PreparedStatement pstmt = connection.prepareStatement(
            "SELECT * FROM Shipment WHERE s_id = ?"
        );
        pstmt.setInt(1, subscriptionId);
        ResultSet rs = pstmt.executeQuery();
        while(rs.next()) {
            int shippingNo = rs.getInt("shipping_no");
            String carrier = rs.getString("carrier");
            String status = rs.getString("status");
            Date shipDate = rs.getDate("ship_date");
            String trackingNo = rs.getString("tracking_no");
            int sid = rs.getInt("s_id");
            shipments.add(new Shipment(shippingNo, carrier, status, shipDate, trackingNo, sid));
        }

        return shipments;
    }

    public void addShipment(int subscriptionId, Shipment newShipment) throws SQLException {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "INSERT INTO Shipment VALUES (?, ?, ?, ?, ?, ?)"
            );
            pstmt.setInt(1, newShipment.getShipping_no());
            pstmt.setString(2, newShipment.getCarrier());
            pstmt.setString(3, newShipment.getStatus());
            pstmt.setDate(4, newShipment.getShip_date());
            pstmt.setString(5, newShipment.getTracking_no());
            pstmt.setInt(6, subscriptionId);
            pstmt.executeUpdate();
            connection.commit();
        } catch(SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void deleteShipment(int shippingNo) throws SQLException {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM Shipment WHERE shipping_no = ?"
            );
            pstmt.setInt(1, shippingNo);
            pstmt.executeUpdate();
            connection.commit();
        } catch(SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public ArrayList<Shipment> getSelectShipments(String selectCarrier, boolean shippingNoCol, boolean carrierCol,
                                                  boolean statusCol, boolean shipDateCol, boolean trackingNoCol,
                                                  boolean subscriptionIdCol) throws SQLException {

        ArrayList<String> dynamicSelection = new ArrayList();
        ArrayList<Shipment> selectShipments = new ArrayList();

        if (shippingNoCol) dynamicSelection.add("shipping_no");
        if (carrierCol) dynamicSelection.add("carrier");
        if (statusCol) dynamicSelection.add("status");
        if (shipDateCol) dynamicSelection.add("ship_date");
        if (trackingNoCol) dynamicSelection.add("tracking_no");
        if (subscriptionIdCol) dynamicSelection.add("s_id");

        String selectionString = String.join(", ", dynamicSelection);
        System.out.println(selectionString);

        PreparedStatement pstmt = connection.prepareStatement(
                "SELECT " + selectionString + " FROM Shipment WHERE carrier = ?"
        );
        pstmt.setString(1, selectCarrier);
        ResultSet rs = pstmt.executeQuery();

        while(rs.next()) {
            int shippingNo = (shippingNoCol) ? rs.getInt("shipping_no") : -1;
            String carrier = (carrierCol) ? rs.getString("carrier") : null;
            String status = (statusCol) ? rs.getString("status") : null;
            Date shipDate = (shipDateCol)? rs.getDate("ship_date") : null;
            String trackingNo = (trackingNoCol) ? rs.getString("tracking_no"): null;
            int sid = (subscriptionIdCol) ? rs.getInt("s_id") : -1;
            selectShipments.add(new Shipment(shippingNo, carrier, status, shipDate, trackingNo, sid));
        }

        return selectShipments;
    }
}

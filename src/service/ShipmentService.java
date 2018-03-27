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

        try {
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
                shipments.add(new Shipment(shippingNo, carrier, status, shipDate, trackingNo));
            }
        } catch(SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
            throw ex;
        }

        return shipments;
    }

    public void addShipments(int subscriptionId, Shipment newShipment) throws SQLException{
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
            System.out.println("Message: " + ex.getMessage());
            try {
                connection.rollback();
            } catch(SQLException ex2) {
                throw ex2;
            }
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
            System.out.println("Message: " + ex.getMessage());
            try {
                connection.rollback();
            } catch(SQLException ex2) {
                throw ex2;
            }
            throw ex;
        }
    }
}

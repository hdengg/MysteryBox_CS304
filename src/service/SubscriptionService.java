package service;

import model.Subscription;
import java.sql.*;

public class SubscriptionService {
    private Connection con;

    public SubscriptionService(Connection connection) {
        con = connection;
    }

    public void addSubscription(int s_id, float cost, boolean status, Date from,
                                int num_months, String username) throws SQLException {
        PreparedStatement ps;

        try {
            ps = con.prepareStatement("INSERT INTO subscription VALUES " +
                    "(?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, s_id);
            ps.setFloat(2, cost);
            ps.setBoolean(3, status);
            ps.setDate(4, from);
            ps.setInt(5, num_months);
            ps.setString(6, username);

            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            // undo the insert
            con.rollback();
        }
    }

    public void deleteSubscription(int sid) throws Exception {
        PreparedStatement ps;
        String deleteString = "DELETE FROM Subscription WHERE s_id = ?";

        try {
            ps = con.prepareStatement(deleteString);
            ps.setInt(1, sid);
            int rowCount = ps.executeUpdate();
            if (rowCount == 0)
                throw new Exception("Subscription " + sid + " does not exist");
            con.commit();
            ps.close();
        } catch (SQLException e) {
            con.rollback();
        }
    }

    public void updateSubscription(int s_id, float cost, boolean status, Date from,
                                   int num_months, String username) throws Exception {
        PreparedStatement ps;
        String updateString =
                "UPDATE Subscription SET s_id = ?, cost = ?, status = ?, s_from = ?, num_months = ?, username = ?" +
                        "WHERE s_id = ?";

        try {
            ps = con.prepareStatement(updateString);
            ps.setInt(1, s_id);
            ps.setFloat(2, cost);
            ps.setBoolean(3, status);
            ps.setDate(4, from);
            ps.setInt(5, num_months);
            ps.setString(6, username);
            update(s_id, ps);

        } catch (SQLException e) {
            con.rollback();
        }
    }

    public void updateStatus(int sid, boolean status) throws Exception {
        PreparedStatement ps;
        String updateString = "update subscription SET status = ? WHERE s_id = ?";

        try {
            ps = con.prepareStatement(updateString);
            ps.setBoolean(1, status);
            ps.setInt(2, sid);
            update(sid, ps);
        } catch (SQLException e) {
            con.rollback();
        }
    }

    // updates database
    // throws an exception if the s_id does not exist
    private void update(int sid, PreparedStatement ps) throws Exception {
        int rowCount = ps.executeUpdate();
        if (rowCount == 0)
            throw new Exception("Subscription " + sid + " does not exist");

        con.commit();
        ps.close();
    }

    public ResultSet getSubscriptions() throws SQLException {
        Statement statement;
        statement = con.createStatement();

        return statement.executeQuery("SELECT * FROM subscription"); //TODO: return a list of subscriptions
    }

    public Subscription getSubscription(int sid) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        Subscription subscription;

        try {
            ps = con.prepareStatement("SELECT * FROM subcription WHERE s_id = ?");
            ps.setInt(1, sid);
            rs = ps.executeQuery();

            if (rs.next()) {
                int s_id = rs.getInt("s_id");
                float cost = rs.getFloat("cost");
                boolean status = rs.getBoolean("status");
                Date s_from = rs.getDate("s_from");
                int num_months = rs.getInt("num_months");
                String username = rs.getString("username");

                return new Subscription(s_id, cost, status, s_from, num_months, username);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return null;
    }

}

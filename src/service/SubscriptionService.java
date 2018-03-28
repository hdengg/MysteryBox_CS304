package service;

import model.Subscription;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;

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

    public List<Subscription> getSubscriptions() {
        Statement st;
        ResultSet rs;
        List<Subscription> subscriptions = new ArrayList<>();
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM subscription");
            while (rs.next()) {
                int s_id = rs.getInt("s_id");
                float cost = rs.getFloat("cost");
                boolean status = rs.getBoolean("status");
                Date s_from = rs.getDate("s_from");
                int num_months = rs.getInt("num_month");
                String username = rs.getString("username");
                Subscription subscription = new Subscription(s_id, cost, status, s_from, num_months, username);
                subscriptions.add(subscription);
            }
            return subscriptions;
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return null;
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
                int num_months = rs.getInt("num_month");
                String username = rs.getString("username");

                return new Subscription(s_id, cost, status, s_from, num_months, username);
            }
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());
        }
        return null;
    }

    public List<Subscription> getSubsFromCust(String username) {
        List<Subscription> subscriptions = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            String query = "SELECT * FROM Subscription WHERE (username = ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                int sid = rs.getInt("s_id");
                float cost = rs.getFloat("cost");
                boolean status = rs.getBoolean("status");
                Date s_from = rs.getDate("s_from");
                int num_months = rs.getInt("num_month");
                Subscription sub = new Subscription(sid, cost, status, s_from, num_months, username);
                subscriptions.add(sub);
            }
            ps.close();
            return subscriptions;
        } catch (SQLException e) {
            System.out.println("Message: " + e.getMessage());
        }
        return null;
    }

    /**
     * Retrieves number of subscriptions for a Mystery Box Theme
     *
     * @param theme the theme
     * @return number of subscriptions
     */
    public int getNumSubsFromTheme(String theme) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            String query = "SELECT mb.theme, COUNT(st.s_id) as total "
                    + "FROM Subscribes_To st NATURAL INNER JOIN Mystery_Box mb "
                    + "WHERE (mb.theme = ?) "
                    + "GROUP BY mb.theme";

            ps = con.prepareStatement(query);
            ps.setString(1, theme);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Message" + e.getMessage());
        }
        return 0;
    }

    /**
     * Retrieves the theme and max subs over all mystery box themes
     * @return theme and number of subs pairing
     */
    public HashMap<String, Integer> getThemeWithMostSubs() {
        Statement st;
        ResultSet rs;
        HashMap<String, Integer> hm = new HashMap<>();
        try {
            st = con.createStatement();
            String query = "WITH temp_table as "
                    + "(SELECT temp.theme, temp.sub_counts "
                    + "FROM (SELECT mb.theme, COUNT(st.s_id) as sub_counts "
                    + "FROM Subscribes_To st NATURAL JOIN Mystery_Box mb "
                    + "GROUP BY mb.theme) temp )"
                    + "SELECT * FROM temp_table "
                    + "WHERE sub_counts = (SELECT MAX(sub_counts) FROM temp_table)";
            rs = st.executeQuery(query);

            if (rs.next()) {
                hm.put(rs.getString("theme"), rs.getInt("sub_counts"));
                return hm;
            }
        } catch (SQLException e) {
            System.out.println("Message" + e.getMessage());
        }
        return null;
    }

    /**
     * Gets usernames subscribed past a certain date
     * @param sub_date the date to query
     * @return list of usernames
     */
    public List<String> getUsersSubscribedAt(Date sub_date) {
        PreparedStatement ps;
        ResultSet rs;
        List<String> usernames = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT(username) "
                    + "FROM Subscription s "
                    + "WHERE (s.s_from > ?)";
            ps = con.prepareStatement(query);
            ps.setDate(1, sub_date);
            rs = ps.executeQuery();

            while (rs.next()) {
                String username = rs.getString("username");
                usernames.add(username);
            }
            return usernames;
        } catch (SQLException e) {
            System.out.println("Message" + e.getMessage());
        }
        return null;
    }
}

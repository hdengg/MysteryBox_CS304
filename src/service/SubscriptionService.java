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

    public void addSubscription(int s_id, boolean status, Date from,
                                int num_months, String username, int mbid) throws SQLException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("INSERT INTO subscription VALUES " +
                    "(?, ?, ?, ?, ?, ?)");
            ps.setInt(1, s_id);
            ps.setBoolean(2, status);
            ps.setDate(3, from);
            ps.setInt(4, num_months);
            ps.setString(5, username);

            ps.executeUpdate();
            con.commit();
            ps.close();
            addSubscribesTo(mbid, s_id);
        } catch (SQLException e) {
            // undo the insert
            con.rollback();
            throw e;
        }
    }

    /**
     * Once subscription is added, add the mbid and s_id to the Subscribes_To table
     * @param mbid mbid to add
     * @param s_id s_id to add
     * @throws SQLException
     */
    private void addSubscribesTo(int mbid, int s_id) throws SQLException {
        PreparedStatement ps;
        try {
            ps = con.prepareStatement("INSERT INTO Subscribes_To VALUES " +
                    "(?, ?)");
            ps.setInt(1, mbid);
            ps.setInt(2, s_id);
            ps.executeUpdate();
            con.commit();
            ps.close();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        }
    }

//    public void deleteSubscription(int sid) throws Exception {
//        PreparedStatement ps;
//        String deleteString = "DELETE FROM Subscription WHERE s_id = ?";
//        try {
//            ps = con.prepareStatement(deleteString);
//            ps.setInt(1, sid);
//            int rowCount = ps.executeUpdate();
//            if (rowCount == 0)
//                throw new Exception("Subscription " + sid + " does not exist");
//            con.commit();
//            ps.close();
//        } catch (SQLException e) {
//            con.rollback();
//            throw e;
//        }
//    }

    public void updateSubscription(int s_id, boolean status, Date from,
                                   int num_months, String username) throws Exception {
        PreparedStatement ps;
        String updateString =
                "UPDATE Subscription SET s_id = ?, status = ?, s_from = ?, num_month = ?, username = ?" +
                        "WHERE s_id = ?";

        try {
            ps = con.prepareStatement(updateString);
            ps.setInt(1, s_id);
            ps.setBoolean(2, status);
            ps.setDate(3, from);
            ps.setInt(4, num_months);
            ps.setString(5, username);
            update(s_id, ps);

        } catch (SQLException e) {
            con.rollback();
            throw e;
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
            throw e;
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

    /**
     * Get all subscriptions from all customers
     * @return all subscriptions
     * @throws SQLException
     */
    public List<Subscription> getSubscriptions() throws SQLException {
        Statement st;
        ResultSet rs;
        List<Subscription> subscriptions = new ArrayList<>();
        st = con.createStatement();
        rs = st.executeQuery("SELECT * FROM subscription");
        while (rs.next()) {
            int s_id = rs.getInt("s_id");
            boolean status = rs.getBoolean("status");
            Date s_from = rs.getDate("s_from");
            int num_months = rs.getInt("num_month");
            String username = rs.getString("username");
            Subscription subscription = new Subscription(s_id, status, s_from, num_months, username);
            subscriptions.add(subscription);
        }
        return subscriptions;
    }

    /**
     * Get subscription from subscription ID
     * @param sid the subscription ID
     * @return the subscription
     * @throws SQLException
     */
    public Subscription getSubscription(int sid) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        Subscription subscription;
        ps = con.prepareStatement("SELECT * FROM subcription WHERE s_id = ?");
        ps.setInt(1, sid);
        rs = ps.executeQuery();

        if (rs.next()) {
            int s_id = rs.getInt("s_id");
            boolean status = rs.getBoolean("status");
            Date s_from = rs.getDate("s_from");
            int num_months = rs.getInt("num_month");
            String username = rs.getString("username");
            return new Subscription(s_id, status, s_from, num_months, username);
        }
        return null;
    }

    /**
     * Get subscribers from customer username
     * @param username the customer username
     * @return list of subscriptions that username has
     * @throws SQLException
     */
    public List<Subscription> getSubsFromCust(String username) throws SQLException {
        List<Subscription> subscriptions = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT * FROM Subscription WHERE (username = ?)";
        ps = con.prepareStatement(query);
        ps.setString(1, username);
        rs = ps.executeQuery();
        while (rs.next()) {
            int sid = rs.getInt("s_id");
            boolean status = rs.getBoolean("status");
            Date s_from = rs.getDate("s_from");
            int num_months = rs.getInt("num_month");
            Subscription sub = new Subscription(sid, status, s_from, num_months, username);
            subscriptions.add(sub);
        }
        ps.close();
        return subscriptions;
    }

    /**
     * Retrieves number of subscriptions for a Mystery Box Theme
     *
     * @param theme the theme
     * @return number of subscriptions
     * @throws SQLException
     */
    public int getNumSubsFromTheme(String theme) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
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
        return 0;
    }

    /**
     * Retrieves the theme and max subs over all mystery box themes
     * @return theme and number of subs pairing
     * @throws SQLException
     */
    public HashMap<String, Integer> getThemeWithMostSubs() throws SQLException {
        Statement st;
        ResultSet rs;
        HashMap<String, Integer> hm = new HashMap<>();
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
        return null;
    }

    /**
     * Gets usernames subscribed past a certain date
     * @param sub_date the date to query
     * @return list of usernames
     * @throws SQLException
     */
    public List<String> getUsersSubscribedAt(Date sub_date) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        List<String> usernames = new ArrayList<>();
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
    }

    private void createView() {
        try {
            Statement st;
            st = con.createStatement();
            String query = "CREATE VIEW join_table AS "
                    + "SELECT * "
                    + "FROM Subscription NATURAL JOIN Mystery_Box NATURAL JOIN Subscribes_To";
            st.executeQuery(query);
        } catch (SQLException e) {
            // silent catch
        }
    }

    private void dropView() {
        try {
            Statement st = con.createStatement();
            st.executeQuery("drop view join_table");
        } catch (SQLException e) {
            // silent catch
        }
    }

    /**
     * Division Query: Gets usernames who are subscribed to all mbox themes
     * @return list of usernames
     * @throws SQLException
     */
    public List<String> getUsersSubscribedToAllThemes() throws SQLException {
        Statement st;
        ResultSet rs;
        createView();
        List<String> usernames = new ArrayList<>();
        st = con.createStatement();
        String query = "SELECT username FROM Subscription S WHERE NOT EXISTS "
                + "(SELECT M.theme FROM Mystery_Box M "
                + "WHERE NOT EXISTS (SELECT * FROM join_table J WHERE "
                + "J.theme = M.theme AND J.s_id = S.s_id))";
        rs = st.executeQuery(query);
        while (rs.next()) {
            String username = rs.getString("username");
            usernames.add(username);
        }
        dropView();
        return usernames;
    }
}

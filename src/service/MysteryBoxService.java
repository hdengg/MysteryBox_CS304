package service;

import model.MysteryBox;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.sql.Date;

/**
 * Created by hdengg on 2018-03-25.
 */
public class MysteryBoxService {

    private Connection connection;

    public MysteryBoxService(Connection connection) {

        this.connection = connection;
    }

    /**
     * Retrieves all mystery boxes
     * @return mystery boxes
     * @throws SQLException
     */
    public List<MysteryBox> getAllMysteryBoxes() throws SQLException {
        List<MysteryBox> mysteryBoxes = new ArrayList<>();
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM Mystery_Box");

        while (rs.next()) {
            int mbid = rs.getInt("mbid");
            int no_items = rs.getInt("no_items");
            Date mdate = rs.getDate("mdate");
            String theme = rs.getString("theme");
            float cost = rs.getFloat("cost");
            MysteryBox mbox = new MysteryBox(mbid, no_items, mdate, theme, cost);
            mysteryBoxes.add(mbox);
        }
        return mysteryBoxes;
    }

    /**
     * Gets mystery box from mbid
     * @param mbid mystery box id
     * @return mystery box
     * @throws SQLException
     */
    public MysteryBox getMysteryBox(int mbid) throws SQLException {
        PreparedStatement ps;
        ResultSet rs;
        ps = connection.prepareStatement("SELECT * FROM Mystery_Box WHERE (mbid = ?)");
        ps.setInt(1, mbid);
        rs = ps.executeQuery();
        if (rs.next()) {
            int mysbid = rs.getInt("mbid");
            int no_items = rs.getInt("no_items");
            Date mdate = rs.getDate("mdate");
            String theme = rs.getString("theme");
            float cost = rs.getFloat("cost");
            return new MysteryBox(mysbid, no_items, mdate, theme, cost);
        }
        ps.close();
        return null;
    }

    /**
     * Adds a mystery box
     * @param mbid mbid to add (will throw SQLException if duplicate)
     * @param no_items number of items in box
     * @param mdate date to offer mystery box
     * @param theme theme of mystery box
     * @param cost cost of box
     * @throws SQLException
     */
    public void addMysteryBox(int mbid, int no_items, Date mdate, String theme, float cost) throws SQLException {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO Mystery_Box VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, mbid);
            ps.setInt(2, no_items);
            ps.setDate(3, mdate);
            ps.setString(4, theme);
            ps.setFloat(5, cost);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    /**
     * Deletes mystery box given mbid
     * @param mbid the mbid of the mystery box to delete
     * @throws SQLException
     */
    public void deleteMysteryBox(int mbid) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Mystery_Box where (mbid = ?)");
            ps.setInt(1, mbid);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    /**
     * Updates mystery box
     * @param mbid mbid of mystery box
     * @param no_items number of items to update
     * @param mdate date to update
     * @param theme theme to update
     * @throws SQLException
     */
    public void updateMysteryBox(int mbid, int no_items, Date mdate, String theme, float cost) throws SQLException {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(
                    "UPDATE Mystery_Box SET no_items = ?, mdate = ?, theme = ?, cost = ? WHERE (mbid = ?)");
            ps.setInt(1, no_items);
            ps.setDate(2, mdate);
            ps.setString(3, theme);
            ps.setFloat(4, cost);
            ps.setInt(5, mbid);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    /**
     * Add item to box
     * @param mbid mystery box id to add item to
     * @param item_id item id to add
     * @throws SQLException
     */
    public void addItemToBox(int mbid, int item_id) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Contains VALUES (?,?)");
            ps.setInt(1, mbid);
            ps.setInt(2, item_id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            updateBoxItemCount(mbid, true);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    public void updateBoxItemCount(int mbid, boolean increase) throws SQLException {
        MysteryBox mb = getMysteryBox(mbid);
        int num_items = increase? (mb.getNo_items() + 1) : (mb.getNo_items() - 1);
        updateMysteryBox(mbid, num_items, mb.getMdate(), mb.getTheme(), (float) mb.getCost());
    }

    /**
     * Delete item from box
     * @param mbid mystery box id to delete
     * @param item_id item_id to delete
     * @throws SQLException
     */
    public void deleteItemFromBox(int mbid, int item_id) throws SQLException {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Contains WHERE (mbid = ?)" + " AND (item_id = ?)");
            ps.setInt(1, mbid);
            ps.setInt(2, item_id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            updateBoxItemCount(mbid, false);
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }

    /**
     * Gets box that has the max or minimum average cost of items
     *
     * @param isMax true for maximum, false for minimum
     * @return tuple containing the box id and the average price
     * @throws SQLException
     */
    public HashMap<Integer, Double> getAverage(boolean isMax) throws SQLException {
        HashMap<Integer, Double> hm = new HashMap<>();
        Statement st = connection.createStatement();
        String query = "WITH inner_table as"
                + "(SELECT temp.mbid, temp.avgprice "
                + "FROM (SELECT Mystery_Box.mbid, AVG (Item.value) AS avgprice "
                + "FROM Mystery_Box LEFT JOIN Contains ON Mystery_Box.mbid = Contains.mbid "
                + "LEFT JOIN Item ON Item.item_id = Contains.item_id "
                + "GROUP BY Mystery_Box.mbid) temp )";
        if (isMax) {
            query = query + "SELECT * FROM inner_table "
                    + "WHERE avgprice = (SELECT MAX(avgprice) FROM inner_table)";
        } else {
            query = query + "SELECT * FROM inner_table "
                    + "WHERE avgprice = (SELECT MIN(avgprice) FROM inner_table)";
        }
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            int mbid = rs.getInt("mbid");
            double avgprice = rs.getDouble("avgprice");
            hm.put(mbid, avgprice);
            return hm;
        }
        return null;
    }

    /**
     * Gets total cost of all subscribed boxes
     * @return total cost
     * @throws SQLException
     */
    public float getRevenue() throws SQLException {
        Statement st = connection.createStatement();
        String query = "SELECT SUM(cost) as total "
                + "FROM Subscribes_To NATURAL JOIN Mystery_Box";
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            return rs.getFloat("total");
        }
        return 0;
    }
}

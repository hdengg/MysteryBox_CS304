package service;

import model.Contains;
import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hdengg on 2018-03-24.
 */
public class ContainsService {

    private Connection connection;

    public ContainsService(Connection connection) {
        this.connection = connection;
    }

    /**
     * Retrieves all contents of the contains table
     * @return contains list
     */
    public List<Contains> getContains() {
        List<Contains> containsList = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Contains");

            while (rs.next()) {
                int mbid = rs.getInt("mbid");
                int item_id = rs.getInt("item_id");

                Contains cs = new Contains(mbid, item_id);
                containsList.add(cs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return containsList;
    }

    /**
     * Add new tuple to the contains table
     * @param mbid mystery box id to add
     * @param item_id item id to add
     */
    public void addContains(int mbid, int item_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Contains VALUES (?,?)");
            ps.setInt(1, mbid);
            ps.setInt(2, item_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete tuple from the contains table
     * @param mbid mystery box id to delete
     * @param item_id item_id to delete
     */
    public void deleteContains(int mbid, int item_id) {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM Contains WHERE mbid = " + mbid + " AND item_id = " + item_id);
            int res = ps.executeUpdate();
            if (res == 1) {
                System.out.println("Item successfully deleted from Mystery Box");
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Join Query: View items from each mystery box
     */
    public void viewItems() {
        try {
            Statement st = connection.createStatement();
            String query = "create view items_from_mbid as "
                    + "select Mystery_Box.mbid, Mystery_Box.theme, Mystery_Box.no_items, "
                    + "Item.item_id, Item.value, Item.item_name "
                    + "from Mystery_Box join Contains on Mystery_Box.mbid = Contains.mbid"
                    + " join Item on Item.item_id = Contains.item_id";
            st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all Items from a specified Mystery Box
     * @param mbid the mbid to query
     * @return all item names associated with mbid
     */
    public List<Item> getItems(int mbid) {
        List<Item> items = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT item_id, value, item_name FROM items_from_mbid WHERE mbid = " + mbid);
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                float value = rs.getFloat("value");
                String item_name = rs.getString("item_name");
                Item item = new Item(item_id, value, item_name);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void dropViewsTable() {
        try {
            Statement st = connection.createStatement();
            st.executeQuery("drop view items_from_mbid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

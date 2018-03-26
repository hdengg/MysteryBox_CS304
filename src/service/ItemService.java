package service;

import model.Item;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hdengg on 2018-03-25.
 */
public class ItemService {

    private Connection connection;

    public ItemService(Connection connection) {

        this.connection = connection;
    }

    /**
     * Retrieves all Items
     * @return items
     */
    public List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM Items");

            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                double value = rs.getDouble("value");
                String item_name = rs.getString("item_name");
                Item item = new Item(item_id, value, item_name);
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * Gets item from item_id
     * @param item_id item_id
     * @return Item
     */
    public Item getItem(int item_id) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("SELECT * FROM Item WHERE (item_id = ?)");
            ps.setInt(1, item_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                int item_id1 = rs.getInt("item_id");
                double value = rs.getDouble("value");
                String item_name = rs.getString("item_name");
                return new Item(item_id1, value, item_name);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds an Item
     * @param item_id item_id
     * @param value value
     * @param item_name name of item
     */
    public void addItem(int item_id, double value, String item_name) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO Item VALUES (?, ?, ?)");
            ps.setInt(1, item_id);
            ps.setDouble(2, value);
            ps.setString(3, item_name);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes Item given item_id
     * @param item_id the item_id of the Item to delete
     */
    public void deleteItem(int item_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Item where (item_id = ?)");
            ps.setInt(1, item_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates an item with item_id, value, and item_name parameters
     * @param item_id id of item
     * @param value price of item
     * @param item_name name of item
     */
    public void updateItem(int item_id, double value, String item_name) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(
                    "UPDATE Item SET value = ?, item_name = ? WHERE (item_id = ?)");
            ps.setDouble(1, value);
            ps.setString(2, item_name);
            ps.setInt(3, item_id);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

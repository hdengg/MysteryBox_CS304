package service;

import model.Contains;
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
            ResultSet rs = st.executeQuery("select * from Contains");

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
     * Add new tuples to the contains table
     * @param mbid mystery box id to add
     * @param item_id item id to add
     */
    public void addContains(int mbid, int item_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("insert into Contains values (?,?)");
            ps.setInt(1, mbid);
            ps.setInt(2, item_id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * Retrieves item ids from mbid
     * @param mbid mystery box ID
     * @return list of item ids
     */
    public List<Integer> getItemIds(int mbid) {
        List<Integer> item_ids = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            String sqlQuery = "select item_id from Contains where mbid = " + mbid;
            ResultSet rs = st.executeQuery(sqlQuery);
            while (rs.next()) {
                item_ids.add(rs.getInt("item_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item_ids;
    }
}

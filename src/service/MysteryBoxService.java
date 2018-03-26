package service;

import model.MysteryBox;

import java.sql.*;
import java.util.ArrayList;
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
     */
    public List<MysteryBox> getMysteryBoxes() {
        List<MysteryBox> mysteryBoxes = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from Mystery_Box");

            while (rs.next()) {
                int mbid = rs.getInt("mbid");
                int no_items = rs.getInt("no_items");
                Date mdate = rs.getDate("mdate");
                String theme = rs.getString("theme");
                MysteryBox mbox = new MysteryBox(mbid, no_items, mdate, theme);
                mysteryBoxes.add(mbox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mysteryBoxes;
    }

    /**
     * Gets mystery box from mbid
     * @param mbid mystery box id
     * @return mystery box
     */
    public MysteryBox getMysteryBox(int mbid) {
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = connection.prepareStatement("SELECT * FROM Mystery_Box WHERE mbid = " + mbid);
            rs = ps.executeQuery();
            if (rs.next()) {
                int mysbid = rs.getInt("mbid");
                int no_items = rs.getInt("no_items");
                Date mdate = rs.getDate("mdate");
                String theme = rs.getString("theme");
                return new MysteryBox(mysbid, no_items, mdate, theme);
            }
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a mystery box
     * @param mbid mbid to add (will throw SQLException if duplicate)
     * @param no_items number of items in box
     * @param mdate date to offer mystery box
     * @param theme theme of mystery box
     */
    public void addMysteryBox(int mbid, int no_items, Date mdate, String theme) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement("INSERT INTO Mystery_Box VALUES (?, ?, ?, ?)");
            ps.setInt(1, mbid);
            ps.setInt(2, no_items);
            ps.setDate(3, mdate);
            ps.setString(4, theme);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes mystery box given mbid
     * @param mbid the mbid of the mystery box to delete
     */
    public void deleteMysteryBox(int mbid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM Mystery_Box where mbid = " + mbid);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMysteryBox(int mbid, int no_items, Date mdate, String theme) {
        PreparedStatement ps;
        try {
            ps = connection.prepareStatement(
                    "UPDATE Mystery_Box SET no_items = ?, mdate = ?, theme = ? WHERE mbid =" + mbid);
            ps.setInt(1, no_items);
            ps.setDate(2, mdate);
            ps.setString(3, theme);
            ps.executeUpdate();
            connection.commit();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

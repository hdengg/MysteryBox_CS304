package service;

import model.Address;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by janicelee on 2018-03-25.
 */
public class AddressService {
    private Connection connection;

    public AddressService(Connection connection) {
        this.connection = connection;
    }

    // get all addresses given a username
    public ArrayList<Address> getAllCustomerAddresses(String username) throws SQLException {
        PreparedStatement pstmt;
        ResultSet rs;
        ArrayList<Address> customerAddresses = new ArrayList<>();
        Address cAddress;
        int houseNum;
        String street;
        String postalCode;
        String city;
        String province;

        pstmt = connection.prepareStatement("SELECT * FROM Customer_Has_Address " +
                "INNER JOIN City_Province ON Customer_Has_Address.postal_code = City_Province.postal_code " +
                "WHERE username = ?");
        pstmt.setString(1, username);
        rs = pstmt.executeQuery();

        while(rs.next()) {
            houseNum = rs.getInt("house_num");
            street = rs.getString("street");
            postalCode = rs.getString("postal_code");
            city = rs.getString("city");
            province = rs.getString("province");
            cAddress = new Address(houseNum, street, postalCode, city, province);
            customerAddresses.add(cAddress);
        }
        pstmt.close();
        return customerAddresses;
    }

    // add a new address for a given customer
    public void addCustomerAddress(String username, int houseNum, String street, String postalCode,
                           String city, String province) throws SQLException {

        PreparedStatement pstmt;
        insertAddress(houseNum, street, postalCode);
        insertCityProvince(city, province, postalCode);

        try {
            // Insert into Customer_Has_Address table
            pstmt = connection.prepareStatement(
                    "INSERT INTO Customer_Has_Address VALUES (?, ?, ?, ?)"
            );
            pstmt.setString(1, username);
            pstmt.setInt(2, houseNum);
            pstmt.setString(3, street);
            pstmt.setString(4, postalCode);
            pstmt.executeUpdate();

            connection.commit();
            pstmt.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                throw ex2;
            }

            throw ex;
        }
    }



    // delete an address
    public void deleteAddress(String username, int houseNum, String street, String postalCode) throws SQLException {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    "DELETE FROM Customer_Has_Address WHERE (username = ?) and (house_num = ?) " +
                            "and (street = ?) and (postal_code = ?)"
            );
            pstmt.setString(1, username);
            pstmt.setInt(2, houseNum);
            pstmt.setString(3, street);
            pstmt.setString(4, postalCode);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch(SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                throw ex2;
            }

             throw ex;
        }
    }

    // update an address
    public void updateAddress(String username, Address currAddress, int houseNum, String street,
                                 String postalCode, String city, String province) throws SQLException {
        PreparedStatement pstmt;
        insertAddress(houseNum, street, postalCode);
        insertCityProvince(city, province, postalCode);

        try {
            // Update the Customer_Has_Address table
            pstmt = connection.prepareStatement(
                    "UPDATE Customer_Has_Address SET house_num = ?, street = ?, postal_code = ? " +
                            "WHERE (username = ?) and (house_num = ?) and (street = ?) and (postal_code = ?)"
            );
            pstmt.setString(1, Integer.toString(houseNum));
            pstmt.setString(2, street);
            pstmt.setString(3, postalCode);
            pstmt.setString(4, username);
            pstmt.setInt(5, currAddress.getHouseNum());
            pstmt.setString(6, currAddress.getStreet());
            pstmt.setString(7, currAddress.getPostalCode());
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                throw ex2;
            }

            throw ex;
        }
    }

    // Inserts an address into the Address table
    private void insertAddress(int houseNum, String street, String postalCode) throws SQLException {
        PreparedStatement pstmt;

        try {
            // Insert into Address table if not already there
            pstmt = connection.prepareStatement(
                    "INSERT INTO Address VALUES (?, ?, ?)"
            );
            pstmt.setInt(1, houseNum);
            pstmt.setString(2, street);
            pstmt.setString(3, postalCode);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();
        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                throw ex2;
            }

            if (!ex.getSQLState().equals(Integer.toString(23000))) {
                throw ex;
            }
        }
    }

    // Inserts a city, province into City_Province table
    private void insertCityProvince(String city, String province, String postalCode) throws SQLException {
        PreparedStatement pstmt;

        try {
            // Insert into City_Province if not already there
            pstmt = connection.prepareStatement(
                    "INSERT INTO City_Province VALUES (?, ?, ?)"
            );
            pstmt.setString(1, city);
            pstmt.setString(2, province);
            pstmt.setString(3, postalCode);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch (SQLException ex) {
            System.out.println("Message: " + ex.getMessage());

            try {
                connection.rollback();
            } catch (SQLException ex2) {
                System.out.println("Message: " + ex2.getMessage());
                throw ex2;
            }

            // only throws errors other than unique constraint violated
            if (!ex.getSQLState().equals(Integer.toString(23000))) {
                throw ex;
            }
        }
    }
}

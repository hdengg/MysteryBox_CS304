package service;

import model.CreditCard;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by janicelee on 2018-03-24.
 */
public class CreditCardService {
    private Connection connection;

    public CreditCardService(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<CreditCard> getCustomerCreditCards(String username) throws SQLException {
        ArrayList<CreditCard> creditCards = new ArrayList();

        PreparedStatement pstmt = connection.prepareStatement(
            "SELECT Credit_Card.* FROM Pays_With INNER JOIN Credit_Card ON Credit_Card.c_id=Pays_With.c_id  " +
                    "WHERE username = ?"
        );
        pstmt.setString(1, username);
        ResultSet rs = pstmt.executeQuery();

        while(rs.next()) {
            int cid = rs.getInt("c_id");
            Date expDate = rs.getDate("exp_date");
            String token = rs.getString("token");
            String type = rs.getString("type");
            int lastDigits = rs.getInt("last_digits");
            creditCards.add(new CreditCard(cid, expDate, token, type, lastDigits));
        }
        pstmt.close();

        return creditCards;
    }

    public void addCustomerCreditCard(String username, CreditCard newCreditCard) throws SQLException {
        PreparedStatement pstmt;
        insertCreditCard(newCreditCard);

        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO Pays_With VALUES(?, ?)"
            );
            pstmt.setInt(1, newCreditCard.getCid());
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void deleteCustomerCreditCard(String username, int cid) throws SQLException {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                "DELETE FROM Pays_With WHERE c_id = ?"
            );
            pstmt.setInt(1, cid);
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    // Inserts a credit card into the Credit_Card table
    private void insertCreditCard(CreditCard newCreditCard) throws SQLException {
        PreparedStatement pstmt;

        try {
            pstmt = connection.prepareStatement(
                    "INSERT INTO Credit_Card (c_id, exp_date, token, type, last_digits)" +
                            "SELECT ?, ?, ?, ?, ? FROM dual " +
                            "WHERE NOT EXISTS (SELECT * FROM Credit_Card WHERE c_id = ?)"
            );
            pstmt.setInt(1, newCreditCard.getCid());
            pstmt.setDate(2, newCreditCard.getExpDate());
            pstmt.setString(3, newCreditCard.getToken());
            pstmt.setString(4, newCreditCard.getType());
            pstmt.setInt(5, newCreditCard.getLastDigits());
            pstmt.setInt(6, newCreditCard.getCid());
            pstmt.executeUpdate();
            connection.commit();
            pstmt.close();

        } catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }
}

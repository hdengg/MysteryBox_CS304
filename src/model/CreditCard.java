package model;


import java.sql.Date;

/**
 * Created by janicelee on 2018-03-24.
 */

public class CreditCard extends Model {
    protected int cid;
    protected Date expDate;
    protected String token;
    protected String type;
    protected int lastDigits;

    public CreditCard(int cid, Date expDate, String token, String type, int lastDigits) {
        this.cid = cid;
        this.expDate = expDate;
        this.token = token;
        this.type = type;
        this.lastDigits = lastDigits;
    }

    public int getCid() {
        return cid;
    }

    public Date getExpDate() {
        return expDate;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public int getLastDigits() {
        return lastDigits;
    }

}

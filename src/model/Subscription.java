package model;

import java.sql.Date;

public class Subscription extends Model {
    protected int s_id;
    protected boolean status;
    protected Date s_from;
    protected int num_months;
    protected String username;

    public Subscription(int s_id, boolean status, Date from, int num_months, String username) {
        this.s_id = s_id;
        this.status = status;
        this.s_from = from;
        this.num_months = num_months;
        this.username = username;
    }

    public int getSid() { return s_id; }
    public boolean isStatus() { return status; }

    public Date getFrom() { return s_from; }

    public int getNum_months() { return num_months; }

    public String getUsername() { return username; }

}

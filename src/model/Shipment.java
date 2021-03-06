package model;

import java.sql.Date;

public class Shipment extends Model {
    protected int shipping_no;
    protected String carrier;
    protected String status;
    protected Date ship_date;
    protected String tracking_no;
    protected int sid;

    public Shipment(int shipping_no, String carrier, String status, Date ship_date, String tracking_no, int sid) {
        this.shipping_no = shipping_no;
        this.carrier = carrier;
        this.status = status;
        this.ship_date = ship_date;
        this.tracking_no = tracking_no;
        this.sid = sid;
    }

    public int getShipping_no() { return shipping_no; }

    public String getCarrier() { return carrier; }

    public String getStatus() { return status; }

    public Date getShip_date() { return ship_date; }

    public String getTracking_no() { return tracking_no; }

    public int getSid() {
        return sid;
    }
}

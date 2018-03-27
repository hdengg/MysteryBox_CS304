package model;


import java.sql.Date;

public class Shipment {
    private int shipping_no;
    private String carrier;
    private String status;
    private Date ship_date;
    private String tracking_no;

    public Shipment(int shipping_no, String carrier, String status, Date ship_date, String tracking_no) {
        this.shipping_no = shipping_no;
        this.carrier = carrier;
        this.status = status;
        this.ship_date = ship_date;
        this.tracking_no = tracking_no;
    }

    public int getShipping_no() { return shipping_no; }

    public String getCarrier() { return carrier; }

    public String getStatus() { return status; }

    public Date getShip_date() { return ship_date; }

    public String getTracking_no() { return tracking_no; }
}

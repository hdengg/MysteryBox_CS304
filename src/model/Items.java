package model;

/**
 * Created by hdengg on 2018-03-24.
 */
public class Items {
    private int item_id;
    private double value;
    private String item_name;

    public Items(int item_id, float value, String item_name) {
        this.item_id = item_id;
        this.value = value;
        this.item_name = item_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public double getValue() {
        return value;
    }

    public int getItem_id() {
        return item_id;
    }
}

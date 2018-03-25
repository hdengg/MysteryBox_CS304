package model;

/**
 * Created by hdengg on 2018-03-24.
 */
public class Contains {
    private int mbid;
    private int item_id;

    public Contains(int mbid, int item_id) {
        this.mbid = mbid;
        this.item_id = item_id;
    }

    public int getMbid() {
        return mbid;
    }

    public int getItem_id() {
        return item_id;
    }
}

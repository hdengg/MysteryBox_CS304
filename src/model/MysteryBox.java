package model;

import java.sql.Date;

/**
 * Created by hdengg on 2018-03-24.
 */
public class MysteryBox {
    private int mbid;
    private int no_items;
    private Date mdate;
    private String theme;

    public MysteryBox(int mbid, int no_items, Date mdate, String theme) {
        this.mbid = mbid;
        this.no_items = no_items;
        this.mdate = mdate;
        this.theme = theme;
    }

    public int getMbid() {
        return mbid;
    }

    public int getNo_items() {
        return no_items;
    }

    public Date getMdate() {
        return mdate;
    }

    public String getTheme() {
        return theme;
    }
}

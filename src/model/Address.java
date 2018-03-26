package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Address extends Model {
    protected int houseNum;
    protected String street;
    protected String postalCode;
    protected String city;
    protected String province;

    public Address(int houseNum, String street, String postalCode, String city, String province) {
        this.houseNum = houseNum;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
    }

    public int getHouseNum() {
        return houseNum;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

}

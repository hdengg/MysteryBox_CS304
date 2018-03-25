package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Address {
    private String houseNum;
    private String street;
    private String postalCode;
    private String city;
    private String province;

    public Address(String houseNum, String street, String postalCode, String city, String province) {
        this.houseNum = houseNum;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
        this.province = province;
    }

    public String getHouseNum() {
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

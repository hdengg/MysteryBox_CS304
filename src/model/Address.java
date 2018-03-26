package model;

/**
 * Created by janicelee on 2018-03-24.
 */
public class Address {
    private int houseNum;
    private String street;
    private String postalCode;
    private String city;
    private String province;

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

    public String toString() {
        return String.format("houseNum:%s, street:%s, postalCode:%s, city:%s, province:%s",
                Integer.toString(houseNum), street, postalCode,  city, province);
    }
}

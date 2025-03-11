package entity;

public class Address {
    private int addressId;
    private String addressLine1;
    private String addressLine2;
    private int pincode;
    private City city;

    public Address() {
    }

    public Address(int addressId, String addressLine1, String addressLine2, int pincode, City city) {
        this.addressId = addressId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.pincode = pincode;
        this.city = city;
    }

    public Address(String addressLine1, String addressLine2, int pincode, City city) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.pincode = pincode;
        this.city = city;
    }

    public Address(int addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", pincode=" + pincode +
                ", city=" + city +
                '}';
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}

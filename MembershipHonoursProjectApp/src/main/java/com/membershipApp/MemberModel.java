package com.membershipApp;

import javafx.beans.property.*;

public class MemberModel {
    private StringProperty name;

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public SimpleStringProperty streetProperty() {
        return street;
    }

    public SimpleStringProperty houseProperty() {
        return house;
    }

    public SimpleIntegerProperty telProperty() {
        return tel;
    }

    public SimpleStringProperty emailProperty() {
        return email;
    }

    public SimpleStringProperty postcodeProperty() {
        return postcode;
    }

    public SimpleStringProperty cityProperty() {
        return city;
    }

    public SimpleObjectProperty dobProperty() {
        return dob;
    }

    public SimpleStringProperty countryProperty() {
        return country;
    }


    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    private IntegerProperty customerId;
    private StringProperty surname;
    private SimpleStringProperty street;
    private SimpleStringProperty house;
    private SimpleIntegerProperty tel;
    private SimpleStringProperty email;
    private SimpleStringProperty postcode;
    private SimpleStringProperty city;
    private SimpleObjectProperty dob;
    private SimpleStringProperty country;

    public String getSurname() {
        return surname.get();
    }

    public int getCustomerId() {
        return customerId.get();
    }

    public String getStreet() {
        return street.get();
    }

    public String getHouse() {
        return house.get();
    }

    public Integer getTel() {
        return tel.get();
    }

    public String getEmail() {
        return email.get();
    }

    public String getPostcode() {
        return postcode.get();
    }

    public String getCity() {
        return city.get();
    }

    public Object getDob() {
        return dob.get();
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getCountry() {
        return country;
    }

    public MemberModel(int id, String name, String surname, String street, String house,
                       int tel, String email, String postcode, String city, Object dob, String country) {

        this.customerId = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.surname = new SimpleStringProperty(surname);
        this.street = new SimpleStringProperty(street);
        this.house = new SimpleStringProperty(house);
        this.tel = new SimpleIntegerProperty(tel);
        this.email = new SimpleStringProperty(email);
        this.postcode = new SimpleStringProperty(postcode);
        this.city = new SimpleStringProperty(city);
        this.dob = new SimpleObjectProperty(dob);
        this.country = new SimpleStringProperty(country);
    }

/*    @Override
    public String toString() {

        return "member" + this.getName() + this.getSurname() + this.getStreet() + this.getHouse() + this.getTel() +
                this.getEmail() + this.getPostcode() + this.getCity() + this.getDob() + this.getCountry();
    }

*/
}



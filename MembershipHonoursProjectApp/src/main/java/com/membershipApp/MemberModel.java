package com.membershipApp;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class MemberModel {


  public SimpleStringProperty nameProperty() {
    return name;
  }

  public SimpleStringProperty surnameProperty() {
    return surname;
  }

  public SimpleStringProperty countryProperty() {
    return country;
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

  public IntegerProperty customerIdProperty() {
    return customerId;
  }

  private SimpleStringProperty name;
  private IntegerProperty customerId;
  private SimpleStringProperty surname;
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

  public String getName() { return name.get();}

  public String getCity() {
    return city.get();
  }

  public Object getDob() {
    return dob.get();
  }

  public String getCountry() {
    return country.get();
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
    this.dob = new SimpleObjectProperty<>(dob);
    this.country = new SimpleStringProperty(country);
  }

  @Override
  public String toString() {
    return "member " + this.getCustomerId() + " " + this.getName() + " " + this.getSurname() + " " + this.getStreet() + " " + this.getHouse() + " " + this.getTel() + " " +
            this.getEmail() + " " + this.getPostcode() + " " + this.getCity() + " " + this.getDob() + " " + this.getCountry();
  }


}



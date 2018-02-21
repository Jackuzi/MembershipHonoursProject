package com.membershipApp;

import javafx.beans.property.*;

import java.sql.Date;

public class MemberModel {


  public SimpleIntegerProperty addressId() {return addressId;}

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

  public SimpleLongProperty telProperty() {
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
  private SimpleLongProperty tel;
  private SimpleStringProperty email;
  private SimpleStringProperty postcode;
  private SimpleStringProperty city;
  private SimpleObjectProperty dob;
  private SimpleStringProperty country;
  private SimpleObjectProperty dFrom;
  private SimpleObjectProperty dTo;
  private SimpleObjectProperty cancelDate;
  private SimpleIntegerProperty expiration;
  private SimpleStringProperty password;
  private SimpleIntegerProperty addressId;

  public String getSurname() {
    return surname.get();
  }

  public int getAddressId() {
    return addressId.get();
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

  public Long getTel() {
    return tel.get();
  }

  public String getEmail() {
    return email.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public void setCustomerId(int customerId) {
    this.customerId.set(customerId);
  }

  public void setSurname(String surname) {
    this.surname.set(surname);
  }

  public void setStreet(String street) {
    this.street.set(street);
  }

  public void setHouse(String house) {
    this.house.set(house);
  }

  public void setTel(Long tel) {
    this.tel.set(tel);
  }

  public void setEmail(String email) {
    this.email.set(email);
  }

  public void setPostcode(String postcode) {
    this.postcode.set(postcode);
  }

  public void setCity(String city) {
    this.city.set(city);
  }

  public void setDob(Object dob) {
    this.dob.set(dob);
  }

  public void setCountry(String country) {
    this.country.set(country);
  }

  public Object getdFrom() {
    return dFrom.get();
  }

  public SimpleObjectProperty dFromProperty() {
    return dFrom;
  }

  public void setdFrom(Object dFrom) {
    this.dFrom.set(dFrom);
  }

  public Object getdTo() {
    return dTo.get();
  }

  public SimpleObjectProperty dToProperty() {
    return dTo;
  }

  public void setdTo(Object dTo) {
    this.dTo.set(dTo);
  }

  public Object getCancelDate() {
    return cancelDate.get();
  }

  public SimpleObjectProperty cancelDateProperty() {
    return cancelDate;
  }

  public void setCancelDate(Object cancelDate) {
    this.cancelDate.set(cancelDate);
  }

  public int getExpiration() {
    return expiration.get();
  }

  public SimpleIntegerProperty expirationProperty() {
    return expiration;
  }

  public void setExpiration(Integer expiration) {
    this.expiration.set(expiration);
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

  public void setAddressId(int addressId) {
    this.addressId.set(addressId);
  }

  public MemberModel(int id, String name, String surname, String street, String house,
                     Long tel, String email, String postcode, String city, Object dob, String country, Date dFrom, Date dTo, Date dCancel, int expiration, String password, int addressId) {
    this.customerId = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.surname = new SimpleStringProperty(surname);
    this.street = new SimpleStringProperty(street);
    this.house = new SimpleStringProperty(house);
    this.tel = new SimpleLongProperty(tel);
    this.email = new SimpleStringProperty(email);
    this.postcode = new SimpleStringProperty(postcode);
    this.city = new SimpleStringProperty(city);
    this.dob = new SimpleObjectProperty<>(dob);
    this.country = new SimpleStringProperty(country);
    //membership data
    this.dFrom = new SimpleObjectProperty<>(dFrom);
    this.dTo = new SimpleObjectProperty<>(dTo);
    this.cancelDate = new SimpleObjectProperty<>(dCancel);
    this.expiration = new SimpleIntegerProperty(expiration);
    this.password = new SimpleStringProperty((password));
    this.addressId = new SimpleIntegerProperty(addressId);

  }

  @Override
  public String toString() {
    return "member " + this.getCustomerId() + " " + this.getName() + " " + this.getSurname() + " " + this.getStreet() + " " + this.getHouse() + " " + this.getTel() + " " +
            this.getEmail() + " " + this.getPostcode() + " " + this.getCity() + " " + this.getDob() + " " + this.getCountry();
  }


  public String getPassword() {
    return password.get();
  }

  public SimpleStringProperty passwordProperty() {
    return password;
  }

  public void setPassword(String password) {
    this.password.set(password);
  }


}



package com.membershipApp;

public class MemberModel {
    private String name;
    private String surname;
    private String street;
    private String house;
    private int tel;
    private String email;
    private String postcode;
    private String city;
    private String dob;

    public String getSurname() {
        return surname;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public int getTel() {
        return tel;
    }

    public String getEmail() {
        return email;
    }

    public String getPostcode() {
        return postcode;
    }

    public String getCity() {
        return city;
    }

    public String getDob() {
        return dob;
    }

    public MemberModel(String name, String surname, String street, String house,
                       int tel, String email, String postcode, String city, String dob){

        this.name = name;
        this.surname = surname;
        this.street = street;
        this.house = house;
        this.tel = tel;
        this.email = email;
        this.postcode = postcode;
        this.city = city;
        this.dob = dob;

    }

    public String getName() {
        return name;
    }
}


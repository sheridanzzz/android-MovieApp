package com.example.mymoviememoir.entity;

import java.util.Date;

public class PersonReg {

    private  String address;
    private  String postcode;
    private  String personid;
    private  String firstname;
    private  String surname;
    private  String gender;
    private  String dob;
    private  String state;

    public PersonReg (){
    }
    public PersonReg (String personid, String firstname, String surname, String gender, String dob, String address, String state, String postcode){
        this.personid =personid;
        this.firstname =firstname;
        this.surname =surname;
        this.gender =gender;
        this.dob =dob;
        this.address =address;
        this.state =state;
        this.postcode =postcode;
    }

    public PersonReg(PersonReg personReg) {
        this.personid = personReg.getPersonid();
        this.firstname = personReg.getFirstname();
        this.surname =personReg.getSurname();
        this.gender =personReg.getGender();
        this.dob =personReg.getDob();
        this.address =personReg.getAddress();
        this.state =personReg.getState();
        this.postcode =personReg.getPostcode();
    }


    public String getPersonid() {
        return personid;
    }
    public void setPersonid(String id) {
        this.personid = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname (String firstname) {
        this.firstname = firstname;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public  String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public  String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public  String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public  String getPostcode() {
        return postcode;
    }
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
    public  String  getDob() {
        return dob;
    }
    public void setDob(String dob) {
        this.dob = dob;
    }

}

package com.example.mymoviememoir.entity;

import java.util.Date;

public class Person {
    private static String address;
    private static int postcode;
    private static int personid;
    private static String firstname;
    private static String surname;
    private static String gender;
    private static String dob;
    private static String state;

    public Person (){
    }
    public Person (int personid, String firstname, String surname, String gender, String dob, String address, String state, int postcode){
        Person.personid =personid;
        Person.firstname =firstname;
        Person.surname =surname;
        Person.gender =gender;
        Person.dob =dob;
        Person.address =address;
        Person.state =state;
        Person.postcode =postcode;
    }

    public Person(int id) {
    }

    public static int getPersonid() {
        return personid;
    }
    public void setPersonid(int id) {
        personid = id;
    }
    public static String getFirstname() {
        return firstname;
    }
    public void setFirstname (String firstname) {
        Person.firstname = firstname;
    }
    public static String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        Person.surname = surname;
    }
    public static String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        Person.gender = gender;
    }
    public static String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        Person.address = address;
    }
    public static String getState() {
        return state;
    }
    public void setState(String state) {
        Person.state = state;
    }
    public static int getPostcode() {
        return postcode;
    }
    public void setPostcode(int postcode) {
        Person.postcode = postcode;
    }
    public static String getDob() {
        return dob;
    }
    public void setDob(String dob) {
        Person.dob = dob;
    }
}



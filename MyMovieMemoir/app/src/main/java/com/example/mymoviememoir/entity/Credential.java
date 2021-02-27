package com.example.mymoviememoir.entity;

import java.util.Date;

public class Credential {
    private int credentialid;
    private String username;
    private String passwordhash;
    private Date signupdate;
    private Person personid;

    public Credential (){
        this.credentialid= 1001;
        this.username= "sheridan";
        this.passwordhash= "";
        this.signupdate= null;
    }

    public Credential (int credentialid, String username, String passwordhash, Date signupdate){
        this.credentialid=credentialid;
        this.username=username;
        this.passwordhash=passwordhash;
        this.signupdate=signupdate;
    }
    public void setPersonid(int id){
        personid =new Person(id);
    }
    public int getPersonid(){
        return personid.getPersonid();
    }
    public int getCredentialid() {
        return credentialid;
    }
    public void setCredentialid(int credentialid) {
        this.credentialid = credentialid;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPasswordhash() {
        return passwordhash;
    }
    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }
    public Date getSignupdate() {
        return signupdate;
    }
    public void setSignupdate(Date signupdate) {
        this.signupdate = signupdate;
    }
}

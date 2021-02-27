package com.example.mymoviememoir.entity;

import java.util.Date;

public class CredentialReg {
    private String credentialid;
    private String username;
    private String passwordhash;
    private String signupdate;
    private PersonReg personid;

    public CredentialReg(){

    }

    public CredentialReg (String credentialid, String username, String passwordhash, String signupdate){
        this.credentialid=credentialid;
        this.username=username;
        this.passwordhash=passwordhash;
        this.signupdate=signupdate;
    }
    public void setPersonid(PersonReg personReg){
        personid =new PersonReg(personReg);
    }
    public PersonReg getPersonid(){
        return personid;
    }
    public String getCredentialid() {
        return credentialid;
    }
    public void setCredentialid(String credentialid) {
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
    public String getSignupdate() {
        return signupdate;
    }
    public void setSignupdate(String signupdate) {
        this.signupdate = signupdate;
    }
}

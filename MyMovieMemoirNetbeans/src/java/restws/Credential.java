/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sheri
 */
@Entity
@Table(name = "CREDENTIAL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Credential.findAll", query = "SELECT c FROM Credential c")
    , @NamedQuery(name = "Credential.findByCredentialid", query = "SELECT c FROM Credential c WHERE c.credentialid = :credentialid")
    , @NamedQuery(name = "Credential.findByUsername", query = "SELECT c FROM Credential c WHERE c.username = :username")
    , @NamedQuery(name = "Credential.findByPasswordhash", query = "SELECT c FROM Credential c WHERE c.passwordhash = :passwordhash")
    , @NamedQuery(name = "Credential.findBySignupdate", query = "SELECT c FROM Credential c WHERE c.signupdate = :signupdate")
    , @NamedQuery(name = "Credential.findByPersonid", query = "SELECT c FROM Credential c WHERE c.personid.personid = :personid")})
public class Credential implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREDENTIALID")
    private Integer credentialid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "PASSWORDHASH")
    private String passwordhash;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SIGNUPDATE")
    @Temporal(TemporalType.DATE)
    private Date signupdate;
    @JoinColumn(name = "PERSONID", referencedColumnName = "PERSONID")
    @ManyToOne(optional = false)
    private Person personid;
  
    

    public Credential() {
    }

    public Credential(Integer credentialid) {
        this.credentialid = credentialid;
    }

    public Credential(Integer credentialid, String username, String passwordhash, Date signupdate) {
        this.credentialid = credentialid;
        this.username = username;
        this.passwordhash = passwordhash;
        this.signupdate = signupdate;
    }

    public Integer getCredentialid() {
        return credentialid;
    }

    public void setCredentialid(Integer credentialid) {
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
    
    public Person getPersonid() {
        return personid;
    }

    public void setPersonid(Person personid) {
        this.personid = personid;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (credentialid != null ? credentialid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Credential)) {
            return false;
        }
        Credential other = (Credential) object;
        if ((this.credentialid == null && other.credentialid != null) || (this.credentialid != null && !this.credentialid.equals(other.credentialid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Credential[ credentialid=" + credentialid + " ]";
    }
    
}

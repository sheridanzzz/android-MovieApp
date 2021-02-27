/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sheri
 */
@Entity
@Table(name = "MEMOIR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Memoir.findAll", query = "SELECT m FROM Memoir m")
    , @NamedQuery(name = "Memoir.findByMemoirid", query = "SELECT m FROM Memoir m WHERE m.memoirid = :memoirid")
    , @NamedQuery(name = "Memoir.findByMoviename", query = "SELECT m FROM Memoir m WHERE m.moviename = :moviename")
    , @NamedQuery(name = "Memoir.findByMoviereleasedate", query = "SELECT m FROM Memoir m WHERE m.moviereleasedate = :moviereleasedate")
    , @NamedQuery(name = "Memoir.findByComment", query = "SELECT m FROM Memoir m WHERE m.comment = :comment")
    , @NamedQuery(name = "Memoir.findByRating", query = "SELECT m FROM Memoir m WHERE m.rating = :rating")
    , @NamedQuery(name = "Memoir.findByWatcheddatetime", query = "SELECT m FROM Memoir m WHERE m.watcheddatetime = :watcheddatetime")
    , @NamedQuery(name = "Memoir.findByMovieNameANDCinemaNameStatic", query = "SELECT m FROM Memoir m WHERE m.moviename = :moviename AND m.cinemaid.cinemaname = :cinemaname")
    , @NamedQuery(name = "Memoir.findByPersonid", query = "SELECT m FROM Memoir m WHERE m.personid.personid = :personid")
    , @NamedQuery(name = "Memoir.findByCinemaid", query = "SELECT m FROM Memoir m WHERE m.cinemaid.cinemaid = :cinemaid")})
public class Memoir implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "MEMOIRID")
    private Integer memoirid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MOVIENAME")
    private String moviename;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MOVIERELEASEDATE")
    @Temporal(TemporalType.DATE)
    private Date moviereleasedate;
    @Size(max = 500)
    @Column(name = "COMMENT")
    private String comment;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATING")
    private double rating;
    @Basic(optional = false)
    @NotNull
    @Column(name = "WATCHEDDATETIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date watcheddatetime;
    @JoinColumn(name = "CINEMAID", referencedColumnName = "CINEMAID")
    @ManyToOne(optional = false)
    private Cinema cinemaid;
    @JoinColumn(name = "PERSONID", referencedColumnName = "PERSONID")
    @ManyToOne(optional = false)
    private Person personid;

    public Memoir() {
    }

    public Memoir(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public Memoir(Integer memoirid, String moviename, Date moviereleasedate, double rating, Date watcheddatetime) {
        this.memoirid = memoirid;
        this.moviename = moviename;
        this.moviereleasedate = moviereleasedate;
        this.rating = rating;
        this.watcheddatetime = watcheddatetime;
    }

    public Integer getMemoirid() {
        return memoirid;
    }

    public void setMemoirid(Integer memoirid) {
        this.memoirid = memoirid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public Date getMoviereleasedate() {
        return moviereleasedate;
    }

    public void setMoviereleasedate(Date moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Date getWatcheddatetime() {
        return watcheddatetime;
    }

    public void setWatcheddatetime(Date watcheddatetime) {
        this.watcheddatetime = watcheddatetime;
    }

    public Cinema getCinemaid() {
        return cinemaid;
    }

    public void setCinemaid(Cinema cinemaid) {
        this.cinemaid = cinemaid;
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
        hash += (memoirid != null ? memoirid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Memoir)) {
            return false;
        }
        Memoir other = (Memoir) object;
        if ((this.memoirid == null && other.memoirid != null) || (this.memoirid != null && !this.memoirid.equals(other.memoirid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "restws.Memoir[ memoirid=" + memoirid + " ]";
    }
    
}

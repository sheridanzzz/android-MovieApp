/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import entities.PersonCinema;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import restws.Credential;
import restws.Memoir;
import restws.Person;

/**
 *
 * @author sheri
 */
@Stateless
@Path("restws.person")
public class PersonFacadeREST extends AbstractFacade<Person> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public PersonFacadeREST() {
        super(Person.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Person entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Person entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Person find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Person> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    
    @GET
    @Path("findByFirstname/{Firstname}")
         @Produces({"application/json"})
         public List<Person> findByFirstname (@PathParam("Firstname") String Firstname)
         {
             Query query = em.createNamedQuery("Person.findByFirstname");
             query.setParameter("firstname", Firstname);
             return query.getResultList();            
    }
         
    @GET
    @Path("findBySurname/{Surname}")
         @Produces({"application/json"})
         public List<Person> findBySurname (@PathParam("Surname") String Surname)
         {
             Query query = em.createNamedQuery("Person.findBySurname");
             query.setParameter("surname", Surname);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByGender/{Gender}")
         @Produces({"application/json"})
         public List<Person> findByGender (@PathParam("Gender") String Gender)
         {
             Query query = em.createNamedQuery("Person.findByGender");
             query.setParameter("gender", Gender);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByDob/{Dob}")
         @Produces({"application/json"})
         public List<Person> findByDob (@PathParam("Dob") String  Dob)
         {
             Date date = null;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(Dob);
 
            }catch (ParseException e) {
            }
          
             Query query = em.createNamedQuery("Person.findByDob");
             query.setParameter("dob", date);
             return query.getResultList();            
    }
     
    @GET
    @Path("findByAddress/{Address}")
         @Produces({"application/json"})
         public List<Person> findByAddress (@PathParam("Address") String Address)
         {
             Query query = em.createNamedQuery("Person.findByAddress");
             query.setParameter("address", Address);
             return query.getResultList();            
    }
    
    @GET
    @Path("findByState/{State}")
         @Produces({"application/json"})
         public List<Person> findByState (@PathParam("State") String State)
         {
             Query query = em.createNamedQuery("Person.findByState");
             query.setParameter("state", State);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByPostcode/{Postcode}")
         @Produces({"application/json"})
         public List<Person> findByPostcode (@PathParam("Postcode") int Postcode)
         {
             Query query = em.createNamedQuery("Person.findByPostcode");
             query.setParameter("postcode", Postcode);
             return query.getResultList();            
    }
         
         
    //Dynamic query for using three attributes
    @GET
       @Path("findByDobGenderANDPostcode/{dob}/{gender}/{postcode}")
       @Produces({"application/json"})
       public List<Person> findByDobGenderANDPostcode(@PathParam("dob") String dob,@PathParam("gender") String gender, @PathParam("postcode") int postcode )
       {
           Date date = null;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(dob);
 
            }catch (ParseException e) {
            }
        TypedQuery<Person> query = em.createQuery("SELECT p FROM Person p WHERE p.dob = :date AND p.gender = :gender AND p.postcode = :postcode", Person.class);
        query.setParameter("date", date);
        query.setParameter("gender", gender);
        query.setParameter("postcode", postcode);
         return query.getResultList();  
       }
       
       
//static query for credential id 
    @GET
    @Path("findBycredentialid/{credentialid}")
         @Produces({"application/json"})
         public List<Person> findBycredentialid (@PathParam("credentialid") Integer credentialid)
         {
             Query query = em.createNamedQuery("Person.findBycredentialid");
             query.setParameter("credentialid", credentialid);
             return query.getResultList();            
    }
      
   

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

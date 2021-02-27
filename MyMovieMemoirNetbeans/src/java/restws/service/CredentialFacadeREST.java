/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
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
import java.text.SimpleDateFormat; 
import javax.persistence.TypedQuery;
import restws.Person;

/**
 *
 * @author sheri
 */
@Stateless
@Path("restws.credential")
public class CredentialFacadeREST extends AbstractFacade<Credential> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public CredentialFacadeREST() {
        super(Credential.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Credential entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Credential entity) {
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
    public Credential find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Credential> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByUsername/{Username}")
         @Produces({"application/json"})
         public List<Credential> findByUsername (@PathParam("Username") String Username)
         {
             Query query = em.createNamedQuery("Credential.findByUsername");
             query.setParameter("username", Username);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByPasswordhash/{Passwordhash}")
         @Produces({"application/json"})
         public List<Credential> findByPasswordhash (@PathParam("Passwordhash") String Passwordhash)
         {
             //char[] c = Passwordhash.toCharArray(); 
             Query query = em.createNamedQuery("Credential.findByPasswordhash");
             query.setParameter("passwordhash", Passwordhash);
             return query.getResultList();            
    }
    
    @GET
    @Path("findBySignupdate/{Signupdate}")
         @Produces({"application/json"})
         public List<Credential> findBySignupdate (@PathParam("Signupdate") String  Signupdate)
         {
             Date date = null;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(Signupdate);
 
            }catch (ParseException e) {
            }
          
             Query query = em.createNamedQuery("Credential.findBySignupdate");
             query.setParameter("signupdate", date);
             return query.getResultList();            
    }
         
         //static query for person id 
    @GET
    @Path("findByPersonid/{personid}")
         @Produces({"application/json"})
         public List<Person> findBycredentialid (@PathParam("personid") Integer personid)
         {
             Query query = em.createNamedQuery("Person.findByPersonid");
             query.setParameter("personid", personid);
             return query.getResultList();            
    }
        
         
       @GET
       @Path("findByEmailANDPasswordhash/{username}/{passwordhash}")
       @Produces({"application/json"})
       public List<Credential> findByEmailANDPasswordhash(@PathParam("username") String username,@PathParam("passwordhash") String passwordhash )
       {
        TypedQuery<Credential> query = em.createQuery("SELECT c FROM Credential c WHERE c.username = :username AND c.passwordhash = :passwordhash", Credential.class);
        query.setParameter("username", username);
        query.setParameter("passwordhash", passwordhash);
        return query.getResultList();  
       }
       
       

         
         
         
         

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

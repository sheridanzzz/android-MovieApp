/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restws.service;

import entities.PersonCinema;
import entities.PersonMovieRating;
import entities.PersonMovieRemake;
import entities.PersonMovies;
import entities.PersonMovies2;
import entities.PersonMoviesYear;
import entities.PersonRating;
import entities.PersonRecentMovies;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import restws.Memoir;

/**
 *
 * @author sheri
 */
@Stateless
@Path("restws.memoir")
public class MemoirFacadeREST extends AbstractFacade<Memoir> {

    @PersistenceContext(unitName = "MyMovieMemoirPU")
    private EntityManager em;

    public MemoirFacadeREST() {
        super(Memoir.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Memoir entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Memoir entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Memoir find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_JSON})
    public List<Memoir> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Memoir> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("findByMoviename/{Moviename}")
         @Produces({"application/json"})
         public List<Memoir> findByFirstname (@PathParam("Moviename") String Moviename)
         {
             Query query = em.createNamedQuery("Memoir.findByMoviename");
             query.setParameter("moviename", Moviename);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByMoviereleasedate/{Moviereleasedate}")
         @Produces({"application/json"})
         public List<Memoir> findByMoviereleasedate (@PathParam("Moviereleasedate") String  Moviereleasedate)
         {
             Date date = null;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                date = sdf.parse(Moviereleasedate);
 
            }catch (ParseException e) {
            }
          
             Query query = em.createNamedQuery("Memoir.findByMoviereleasedate");
             query.setParameter("moviereleasedate", date);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByComment/{Comment}")
         @Produces({"application/json"})
         public List<Memoir> findByComment (@PathParam("Comment") String Comment)
         {
             Query query = em.createNamedQuery("Memoir.findByComment");
             query.setParameter("comment", Comment);
             return query.getResultList();            
    }
         
    @GET
    @Path("findByRating/{Rating}")
         @Produces({"application/json"})
         public List<Memoir> findByRating (@PathParam("Rating") Double Rating)
         {
             Query query = em.createNamedQuery("Memoir.findByRating");
             query.setParameter("rating", Rating);
             return query.getResultList();            
    } 
         
    @GET
    @Path("findByWatcheddatetime/{Watcheddatetime}")
         @Produces({"application/json"})
         public List<Memoir> findByWatcheddatetime (@PathParam("Watcheddatetime") String Watcheddatetime)
         {
             Date date = null;
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            try {
                date = sdf.parse(Watcheddatetime);
 
            }catch (ParseException e) {
            }
          
             Query query = em.createNamedQuery("Memoir.findByWatcheddatetime");
             query.setParameter("watcheddatetime", date);
             return query.getResultList();          
    }
         
    @GET
         @Path("findByPersonid/{personid}")
         @Produces({"application/json"})
         public List<Memoir> findByPersonid (@PathParam("personid") int personid)
         {
             Query query = em.createNamedQuery("Memoir.findByPersonid");
             query.setParameter("personid", personid);
             return query.getResultList();            
    }
         
         @GET
         @Path("findByCinemaid/{cinemaid}")
         @Produces({"application/json"})
         public List<Memoir> findByCinemaid (@PathParam("cinemaid") int cinemaid)
         {
             Query query = em.createNamedQuery("Memoir.findByCinemaid");
             query.setParameter("cinemaid", cinemaid);
             return query.getResultList();            
    }
       
         
         
    //Dynamic query for implicit join
    @GET
       @Path("findByMovieNameANDCinemaName/{moviename}/{cinemaname}")
       @Produces({"application/json"})
       public List<Memoir> findByMovieNameANDCinemaName(@PathParam("moviename") String moviename,@PathParam("cinemaname") String cinemaname)
       {
        TypedQuery<Memoir> query = em.createQuery("SELECT m FROM Memoir m WHERE m.moviename = :moviename AND m.cinemaid.cinemaname = :cinemaname",
                Memoir.class);
        query.setParameter("moviename", moviename);
        query.setParameter("cinemaname", cinemaname);
         return query.getResultList();  
       }
       
   
     //Static query for implicit join 
     @GET
     @Path("findByMovieNameANDCinemaNameStatic/{moviename}/{cinemaname}")
         @Produces({"application/json"})
         public List<Memoir> findByMovieNameANDCinemaNameStatic (@PathParam("moviename") String moviename, @PathParam("cinemaname") String cinemaname )
         {
             Query query = em.createNamedQuery("Memoir.findByMovieNameANDCinemaNameStatic");
             query.setParameter("moviename", moviename);
             query.setParameter("cinemaname", cinemaname);
             return query.getResultList();            
    } 
     
         //Task 4A
       @GET
       @Path("findTotalMoviesWatched/{personid}/{startDate}/{endDate}")
       @Produces({MediaType.APPLICATION_JSON})
        public List<PersonCinema> findTotalMoviesWatched(@PathParam("personid") int personid, @PathParam("startDate") String startDate, @PathParam("endDate") String endDate){            
            Date dateStart = null;
            Date dateEnd = null;
            String startDate1 = startDate;
            String endDate1 = endDate;
            startDate1 = startDate1.concat(" 00:00:00.000");
            endDate1 = endDate1.concat(" 00:00:00.000");
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            try {
                dateStart = sdf.parse(startDate1);
                dateEnd = sdf.parse(endDate1);
 
            }catch (ParseException e) {
            }
         TypedQuery<PersonCinema> query = em.createQuery("SELECT new entities.PersonCinema(m.cinemaid.postcode, count( m.cinemaid.postcode)) From Memoir m WHERE m.personid.personid = :personid AND m.watcheddatetime BETWEEN :startDate AND :endDate GROUP BY m.cinemaid.postcode",PersonCinema.class);
         query.setParameter("personid", personid);
         query.setParameter("startDate", dateStart);
         query.setParameter("endDate", dateEnd);
         
         return query.getResultList();
        }
        
        //task 4B
        @GET
        @Path("findTotalMoviesWatchedPerMonth/{personid}/{year}")
        @Produces({MediaType.APPLICATION_JSON})
        public List<PersonMovies2> findTotalMoviesWatchedPerMonth(@PathParam("personid") int personid, @PathParam("year") int year){            
         TypedQuery<PersonMovies> query = em.createQuery("SELECT new entities.PersonMovies ( FUNC('MONTH', m.watcheddatetime), count( FUNC('MONTH', m.watcheddatetime) ) ) From Memoir m WHERE m.personid.personid = :personid AND FUNC('YEAR', m.watcheddatetime) = :year GROUP BY FUNC('MONTH', m.watcheddatetime)",PersonMovies.class);
         query.setParameter("personid", personid);
         query.setParameter("year", year);
          List<PersonMovies> resultList= query.getResultList(); 
         List<PersonMovies2> resultList2 = new ArrayList<>();
         for (int i = 0; i < resultList.size(); i++) { 
             PersonMovies2 pm = new PersonMovies2();
             String monthname;
             Long total;
           int month = resultList.get(i).getMonth();
               switch (month) {
                   case 1:
                       {
                            monthname = "Jan";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                   case 2:
                       {
                            monthname = "Feb";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                    case 3:
                       {
                            monthname = "Mar";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                    case 4:
                       {
                            monthname = "Apr";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                    case 5:
                       {
                            monthname = "May";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                   case 6:
                       {
                            monthname = "Jun";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                   case 7:
                       {
                            monthname = "Jul";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                    case 8:
                       {
                            monthname = "Aug";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                   case 9:
                       {
                            monthname = "Sept";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }
                   case 10:
                       {
                            monthname = "Oct";
                            total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i,pm );
                           break;
                       }   
                   case 11:
                       {
                           monthname = "Nov";
                           total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i ,pm );
                           break;
                       }
                   case 12:
                       {
                            monthname = "Dec";
                           total = resultList.get(i).getTotalMoviesWatched();
                             pm.setMonth(monthname);
                             pm.setTotalMoviesWatched(total);
                           resultList2.add(i ,pm );
                           break;
                       }
                   default:
                       break;
               }
               }          
         return resultList2;
        }
        
        //task 4C
        @GET
        @Path("findHighestRatedMovie/{personid}")
        @Produces({MediaType.APPLICATION_JSON})
        public List<PersonRating> findHighestRatedMovie(@PathParam("personid") int personid){            
         TypedQuery<PersonRating> query = em.createQuery("SELECT new entities.PersonRating (m.moviename, m.rating, m.moviereleasedate ) From Memoir m WHERE m.personid.personid = :personid AND m.rating = (SELECT MAX(m2.rating) FROM Memoir m2 WHERE m2.personid.personid = :personid )", PersonRating.class);
         query.setParameter("personid", personid);
         return query.getResultList();
        }
        
        //task 4D
        @GET
        @Path("findMoviesWatchedReleased/{personid}")
        @Produces({MediaType.APPLICATION_JSON})
        public List<PersonMoviesYear> findMoviesWatchedReleased(@PathParam("personid") int personid){            
         TypedQuery<PersonMoviesYear> query = em.createQuery("SELECT new entities.PersonMoviesYear (m.moviename, EXTRACT(YEAR FROM m.moviereleasedate) ) From Memoir m WHERE m.personid.personid = :personid AND EXTRACT(YEAR FROM m.moviereleasedate) = EXTRACT(YEAR FROM m.watcheddatetime)", PersonMoviesYear.class);
         query.setParameter("personid", personid);
         return query.getResultList();
        }
        
        //Task 4E
        @GET
        @Path("findMovieRemake/{personid}")
        @Produces({MediaType.APPLICATION_JSON})
        public List<PersonMovieRemake> findMovieRemake(@PathParam("personid") int personid){            
         TypedQuery<PersonMovieRemake> query = em.createQuery("SELECT new entities.PersonMovieRemake (m.moviename, EXTRACT(YEAR FROM m.moviereleasedate) ) From Memoir m WHERE m.personid.personid = :personid AND m.moviename in (select q.moviename from Memoir q group by q.moviename HAVING COUNT(q.moviename)>1)", PersonMovieRemake.class);
         query.setParameter("personid", personid);
         return query.getResultList();
        }
        
        //Task 4F
      @GET
        @Path("findHighestRatedMoviesRecentYear/{personid}")
        @Produces({MediaType.APPLICATION_JSON})
        public List<PersonMovieRating> findHighestRatedMoviesRecentYear(@PathParam("personid") int personid){  
       
        Date d=new Date();  
        int year=d.getYear();  
        int currentYear=year+1900;  
         TypedQuery<PersonMovieRating> query = em.createQuery("SELECT new entities.PersonMovieRating (m.moviename, m.rating, m.moviereleasedate) From Memoir m WHERE m.personid.personid = :personid AND EXTRACT(YEAR FROM m.moviereleasedate) = :recentyear ORDER BY m.rating DESC", PersonMovieRating.class);
         query.setParameter("personid", personid);
         query.setParameter("recentyear", currentYear);
         List<PersonMovieRating> resultList= query.setMaxResults(5).getResultList();                  
         return resultList;
        }
        
        //TopfiveMoviesFor2020
        @GET
        @Path("findMoviesWatched2020/{personid}")
        @Produces({MediaType.APPLICATION_JSON})
        public List<PersonRecentMovies> findMoviesWatched2020(@PathParam("personid") int personid){ 
        Date d=new Date();  
        int year=d.getYear();  
        int currentYear=year+1900;  
         TypedQuery<PersonRecentMovies> query = em.createQuery("SELECT new entities.PersonRecentMovies (m.moviename, m.moviereleasedate, m.rating) From Memoir m WHERE m.personid.personid = :personid AND EXTRACT(YEAR FROM m.watcheddatetime) = :recentyear ORDER BY m.rating DESC", PersonRecentMovies.class);
         query.setParameter("personid", personid);
         query.setParameter("recentyear", currentYear);
         return query.getResultList();
        }
        
       
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

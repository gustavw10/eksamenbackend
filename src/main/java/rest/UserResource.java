/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BreedCombinedDTO;
import dtos.BreedDTO;
import dtos.BreedFactDTO;
import dtos.BreedInfoDTO;
import dtos.BreedLinkDTO;
import dtos.UsersDTO;
//import entities.Breed;
import entities.Dog;
import entities.User;
import dtos.DogsDTO;
import dtos.DogDTO;
import dtos.SearchesDTO;
import entities.Breed;
import entities.SearchDate;
import errorhandling.NotFoundException;
import facades.DogFacade;
import facades.UserFacade;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;
import utils.HttpUtils;

/**
 * REST Web Service
 
 */


@Path("info")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final UserFacade FACADE =  UserFacade.getUserFacade(EMF);
    private static final DogFacade DOGFACADE = DogFacade.getDogFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    //added
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }
    
    @Path("count")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getRenameMeCount() {
        long count = FACADE.getUserCount();
        //System.out.println("--------------->"+count);
        return "{\"count\":"+count+"}";  //Done manually so no need for a DTO
    }
    
    
    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON})
    public String getAllUsers() {
        UsersDTO users = FACADE.getAllUsers();
        return GSON.toJson(users);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("allCount")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery ("select u from User u",entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("user")
    @RolesAllowed({"user", "admin"})
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello, user. You are logged into '" + thisuser + "'\"}";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("searches")
    @RolesAllowed({"user", "admin"})
    public String getSearches() throws NotFoundException {
        String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        
        SearchesDTO search = DOGFACADE.getSearchDTO();
        
        return GSON.toJson(search);
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addDog/{id}")
    @RolesAllowed({"user", "admin"})
    public String addDog(@PathParam("id") long id, String dog) {
        //String thisuser = "admin";
        String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        
        DogDTO dogToAdd = GSON.fromJson(dog, DogDTO.class);
        DogDTO dogReturn = DOGFACADE.addUserDog(dogToAdd, thisuser, id);
             
        return GSON.toJson(dogReturn);
    }
    
    @DELETE
    @Path("deleteDog/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public String deleteDog(@PathParam("id") long id)   {
        DogDTO dog = DOGFACADE.deleteDog(id);
        return GSON.toJson(dog);
    }
    
    @PUT
    @Path("editDog/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    public String updateDog(@PathParam("id") long id,  String dog) {
        String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        
        DogDTO dogToAdd = GSON.fromJson(dog, DogDTO.class);
        DogDTO dogReturn = DOGFACADE.editUserDog(dogToAdd, id);
        
        return GSON.toJson(dogReturn);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("breeds")
    public String getBreeds() throws IOException{
        String breeds = HttpUtils.fetchData("https://dog-info.cooljavascript.dk/api/breed");
        
        BreedDTO breed = GSON.fromJson(breeds, BreedDTO.class);
        return GSON.toJson(breed);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("breedInfo/{id}")
    public String getBreedInfo(@PathParam("id") String id) throws IOException{
        String breed1 = HttpUtils.fetchData("https://dog-info.cooljavascript.dk/api/breed/" + id);
        String breed2 = HttpUtils.fetchData("https://dog-image.cooljavascript.dk/api/breed/random-image/" + id);
        String breed3 = HttpUtils.fetchData("https://dog-api.kinduff.com/api/facts");
        
        BreedInfoDTO breedInfo = GSON.fromJson(breed1, BreedInfoDTO.class);
        BreedLinkDTO breedLink = GSON.fromJson(breed2, BreedLinkDTO.class);
        BreedFactDTO breedFact = GSON.fromJson(breed3, BreedFactDTO.class);
        
        BreedCombinedDTO comb = new BreedCombinedDTO(breedFact, breedInfo, breedLink);
        
        EntityManager em = EMF.createEntityManager();
        em.getTransaction().begin();
        SearchDate date = new SearchDate(breedInfo.getBreed());
        em.persist(date);
        em.getTransaction().commit();
        em.close();
        
        return GSON.toJson(comb);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("dogs")
    @RolesAllowed({"user", "admin"})
    public String getUserDogs() throws NotFoundException {
        String thisuser = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        
        DogsDTO dogs = DOGFACADE.getUserDogs(thisuser);
        
        return GSON.toJson(dogs);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        return "{\"msg\": \"Hello to (admin) User: " + thisuser + "\"}";
    }
    
    public static void main(String[] args) {
        
    }
}
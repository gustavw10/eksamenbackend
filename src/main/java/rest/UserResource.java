/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.UsersDTO;
//import entities.Breed;
import entities.Dog;
import entities.User;
import dtos.DogsDTO;
import dtos.DogDTO;
import entities.Breed;
import facades.DogFacade;
import facades.UserFacade;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
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
 *
 * @author maddy
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
    
//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("addDog")
//    @RolesAllowed({"user", "admin"})
//    public String addDog(String dogo, long breed) {
//        String thisuser = securityContext.getUserPrincipal().getName();
//        String thisuser = "user";
//        EntityManager em = EMF.createEntityManager();
//        User user = em.find(User.class, thisuser);
//        Breed br = em.find(Breed.class, breed);
//        DogDTO dog = GSON.fromJson(dogo, DogDTO.class);
//        em.getTransaction().begin();
//        
//        Dog dogToAdd = new Dog(dog.getName(), dog.getDateOfBirth(), dog.getInfo());  
//        dogToAdd.setBoth(user, br);
//        
//        System.out.println("user: " + dogToAdd.getUser().getUserName());
//        System.out.println("breed: " + dogToAdd.getBreed().getName());
//        
//        em.persist(dogToAdd);
//        em.getTransaction().commit();
//        
////        
////        DogDTO dogToAdd = GSON.fromJson(dog, DogDTO.class);
////        DogDTO dogFinal = DOGFACADE.addDog(dogToAdd, user, breed);
//        
////        return GSON.toJson(dogFinal);
//        return "";
//    }
    
//    @PUT
//    @Path("authoredit/{id}")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    public String updatePerson(@PathParam("id") long id,  String person) {
//        AuthorDTO pers = GSON.fromJson(person, AuthorDTO.class);
//        pers.setId(id);
//        AuthorDTO returnPerson = AUTFACADE.editAuthor(pers);
//        return GSON.toJson(returnPerson);
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("addDog/{id}")
    @RolesAllowed({"user", "admin"})
    public String addDog(@PathParam("id") long id, String dog) {
        
        //String thisuser = securityContext.getUserPrincipal().getName();
        String thisuser = "admin";
        EntityManager em = EMF.createEntityManager();
        
        DogDTO dogToAdd = GSON.fromJson(dog, DogDTO.class);
        DogDTO dogReturn = DOGFACADE.addUserDog(dogToAdd, thisuser, id);
             
        return GSON.toJson(dogReturn);
    }
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("dogs")
    @RolesAllowed({"user", "admin"})
    public String getUserDogs() {
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
        
        EntityManager em = EMF.createEntityManager();
        //DogsDTO dogs = DOGFACADE.getUserDogs(thisuser);
        String let ="{\"name\": \"TOOGOOD\",\n" +
"            \"dateOfBirth\": \"01/11/2011\",\n" +
"            \"info\": \"newest\"}";
        
        UserResource res = new UserResource();        
        String dog = res.addDog(5, let);
    }
}
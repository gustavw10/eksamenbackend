/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.DogDTO;
import dtos.DogsDTO;
import dtos.SearchesDTO;
import entities.Breed;
//import entities.Breed;
import entities.Dog;
import entities.User;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author Gustav
 */
public class DogFacade {
    
    private static EntityManagerFactory emf;
    private static DogFacade instance; 
    
    private DogFacade() {
        
    }
        public static DogFacade getDogFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new DogFacade();
        }
        return instance;
    }
    
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
     public SearchesDTO getSearchDTO() throws NotFoundException{
        EntityManager em = getEntityManager();
        
         try {
            SearchesDTO search = new SearchesDTO(em.createQuery("SELECT e FROM SearchDate e ").getResultList());
            if (search == null) {
                throw new NotFoundException("Could not find anything with the given query.");
            } else {
                return search;
            }
        } finally {
            em.close();
        }
    }
    
    public DogsDTO getUserDogs(String user) throws NotFoundException{
        EntityManager em = getEntityManager();
        
         try {
            DogsDTO dogs = new DogsDTO(user, em.createQuery("SELECT d from Dog d " ).getResultList());
            if (dogs == null) {
                throw new NotFoundException("Could not find anything with the given query.");
            } else {
                return dogs;
            }
        } finally {
            em.close();
        }
    }
    
    public DogDTO addUserDog(DogDTO dogDto, String thisuser, long breed){
        EntityManager em = getEntityManager();
        
        User user = em.find(User.class, thisuser);
        Breed br = em.find(Breed.class, breed);
        
        em.getTransaction().begin();
        Dog dogToAdd = new Dog(dogDto.getName(), dogDto.getDateOfBirth(), dogDto.getInfo());  
        dogToAdd.setBoth(user, br);
        em.persist(dogToAdd);
        em.getTransaction().commit();
        em.close();
        
        return new DogDTO(dogToAdd);
    }
    
     public DogDTO editUserDog(DogDTO dogDTO, long id) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            Dog dog = em.find(Dog.class, id);
            dog.setName(dogDTO.getName());
            dog.setInfo(dogDTO.getInfo());
            dog.setDateOfBirth(dogDTO.getDateOfBirth());
            em.getTransaction().commit();
            return new DogDTO(dog);
        } finally {
            em.close();
        }
    }
     
     public DogDTO deleteDog(long id) {
        EntityManager em = getEntityManager();
        Dog dog = em.find(Dog.class, id);
        if (dog == null) {
            //throw new PersonNotFoundException("Could not delete, provided id does not exist");
        } else {
            try {
                em.getTransaction().begin();
                em.remove(dog);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
        return new DogDTO(dog);
    }
    
}

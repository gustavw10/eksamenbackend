/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.DogDTO;
import dtos.DogsDTO;
import entities.Breed;
//import entities.Breed;
import entities.Dog;
import entities.User;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

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
    
    public DogsDTO getUserDogs(String user){
        EntityManager em = getEntityManager();
        
         try {
            DogsDTO dogs = new DogsDTO(user, em.createQuery("SELECT d from Dog d " + "").getResultList());
            if (dogs == null) {
                throw new UnsupportedOperationException("error handler coming here");
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
    
}

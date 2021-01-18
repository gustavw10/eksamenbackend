package utils;


//import entities.Breed;
import entities.Breed;
import entities.Dog;
import entities.Role;
import entities.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class SetupTestUsers {

  public static void main(String[] args) {

    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();
    
    // IMPORTAAAAAAAAAANT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // This breaks one of the MOST fundamental security rules in that it ships with default users and passwords
    // CHANGE the three passwords below, before you uncomment and execute the code below
    // Also, either delete this file, when users are created or rename and add to .gitignore
    // Whatever you do DO NOT COMMIT and PUSH with the real passwords

    User user = new User("user", "test12345");
    User admin = new User("admin", "test123");
    User both = new User("user_admin", "test123");

    if(admin.getUserPass().equals("test")||user.getUserPass().equals("test")||both.getUserPass().equals("test"))
      throw new UnsupportedOperationException("You have not changed the passwords");

    em.getTransaction().begin();
    Dog firstDog = new Dog("Fido", "10/11/2004", "First dog");
    Dog secDog = new Dog("Growl", "05/11/2008", "Second dog");
    Dog thirdDog = new Dog("Mejse", "09/08/2014", "The third dog");
    Breed breed = new Breed("Boxer", "The boxer is a medium to large, short haired breed of dog.");
    Breed breedTwo = new Breed("Keeshond", "The keeshond is a medium size dog with a plush coat.");

    Role userRole = new Role("user");
    Role adminRole = new Role("admin");
    user.addRole(userRole);
    admin.addRole(adminRole);
    both.addRole(userRole);
    both.addRole(adminRole);
    
    
        firstDog.setBoth(user, breed);
        secDog.setBoth(admin, breed);
        thirdDog.setBoth(admin, breedTwo);
    
    em.persist(userRole);
    em.persist(adminRole);
    em.persist(user);
    em.persist(admin);
    em.persist(both);
    em.persist(firstDog);
    em.persist(secDog);
    em.persist(thirdDog);
    em.getTransaction().commit();
    System.out.println("PW: " + user.getUserPass());
    System.out.println("Testing user with OK password: " + user.verifyPassword("test"));
    System.out.println("Testing user with wrong password: " + user.verifyPassword("test1"));
    System.out.println("Created TEST Users");
   
  }

}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustav
 */
@Entity
public class Dog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String dateOfBirth;
    private String info;
    
    @ManyToOne(cascade = { CascadeType.ALL })
    private User user;
    
    @ManyToOne(cascade = { CascadeType.ALL })
    private Breed breed;
    
    public Dog(){
        
    }

    public Dog(String name, String dateOfBirth, String info) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }
    
    public void setUser(User user) {
        if (user != null){
            this.user = user;
            user.addDog(this);
        }
    }
    
    public void setBreed(Breed breed) {
        if (breed != null){
            this.breed = breed;
            breed.addDog(this);
        }
    }
    
    public void setBoth(User user, Breed breed) {
        if (breed != null && user != null){
            this.breed = breed;
            this.user = user;
            breed.addDog(this);
            user.addDog(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getUser() {
        return user;
    }

    public Breed getBreed() {
        return breed;
    }
    
}

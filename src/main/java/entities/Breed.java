/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Gustav
 */
@Entity
public class Breed implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String name;
    private String info;
    
    @OneToMany(mappedBy="breed", cascade = CascadeType.ALL)
    private List<Dog> dogs;
    
    public Breed(){
        
    }

    public Breed(String name, String info) {
        this.name = name;
        this.info = info;
        this.dogs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    
    public void addDog(Dog dog) {
        if (dog != null){
            dogs.add(dog);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }
}

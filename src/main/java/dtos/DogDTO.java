/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Dog;

/**
 *
 * @author Gustav
 */
public class DogDTO {
    
    private Long id;
    private String name;
    private String dateOfBirth;
    private String info;

    public DogDTO(Long id, String name, String dateOfBirth, String info) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }
    
    public DogDTO( String name, String dateOfBirth, String info) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.info = info;
    }
    
    public DogDTO(Dog dog){
        this.id = dog.getId();
        this.name = dog.getName();
        this.dateOfBirth = dog.getDateOfBirth();
        this.info = dog.getInfo();
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
    
}

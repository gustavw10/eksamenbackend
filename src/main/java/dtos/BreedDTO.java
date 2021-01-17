/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.util.List;

/**
 *
 * @author Gustav
 */
public class BreedDTO {
    
    public List<DogBreed> dogs;
    
    class DogBreed {
        public String breed;

        public String getBreed() {
            return breed;
        }
    }

    public List<DogBreed> getDogs() {
        return dogs;
    }

    public void setDogs(List<DogBreed> dogs) {
        this.dogs = dogs;
    }
    
}

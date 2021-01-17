/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Dog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustav
 */
public class DogsDTO {
    
    private List<DogDTO> dogsDTO = new ArrayList();

    public DogsDTO(String user, List<Dog> dogs) {
//        final int loop = 0;
//        System.out.println(dogs.get(0).getUser().getUserName());
//        dogs.forEach((p) -> { 
//            
//            if(dogs.get(loop).getUser().getUserName() == "Tom"){
//            dogsDTO.add(new DogDTO(p));
//            }
//        });
        
        for(int i = 0; i < dogs.size(); i++){
            if(dogs.get(i).getUser().getUserName().equals(user)){
            dogsDTO.add(new DogDTO(dogs.get(i)));
            }
        }
        
    }

    public List<DogDTO> getDogsDTO() {
        return dogsDTO;
    }

    public void setDogsDTO(List<DogDTO> dogsDTO) {
        this.dogsDTO = dogsDTO;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

/**
 *
 * @author Gustav
 */
public class BreedCombinedDTO {
    
    public String breed;
    //public String info;
    public String wikipedia;
    public String image;
    public String facts;
    
    public BreedCombinedDTO(BreedFactDTO dto1, BreedInfoDTO dto2, BreedLinkDTO dto3){
        this.breed = dto2.getBreed();
        //this.info = dto2.getInfo();
        this.wikipedia = dto2.getWikipedia();
        this.image = dto3.getImage();
        this.facts = dto1.getFacts().get(0);
    }
    
    public BreedCombinedDTO(String one, String two, String three, String four, String five){
        this.breed = one;
        //this.info = two;
        this.wikipedia = three;
        this.image = four;
        this.facts = five;
    }
}

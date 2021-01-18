/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.SearchDate;
import java.text.Format;
import java.text.SimpleDateFormat;

/**
 *
 * @author Gustav
 */
public class SearchDTO {
    
    public String date;
    public String breed; 

    public SearchDTO() {
    }

    public SearchDTO(SearchDate date) {
        Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.date = formatter.format(date.getDate());
        this.breed = date.getBreed();
    }
    
}

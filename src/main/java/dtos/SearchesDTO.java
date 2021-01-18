/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.SearchDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustav
 */
public class SearchesDTO {
    
    private List<SearchDTO> searches = new ArrayList();
    
    public SearchesDTO(List<SearchDate> search) {
        search.forEach((p) -> { 
            searches.add(new SearchDTO(p));
      });
    }

    public List<SearchDTO> getSearches() {
        return searches;
    }

    public void setSearches(List<SearchDTO> searches) {
        this.searches = searches;
    }
}
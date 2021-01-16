
package dtos;

import entities.User;
import java.util.ArrayList;
import java.util.List;

public class UsersDTO {
    
       List<UserDTO> all = new ArrayList();

    public UsersDTO(List<User> userEntities) {
        userEntities.forEach((u) -> {
            all.add(new UserDTO(u));
        });
    }

    public UsersDTO() {
    }

    public List<UserDTO> getAll() {
        return all;
    }

    public void setAll(List<UserDTO> all) {
        this.all = all;
    }
    
    
}

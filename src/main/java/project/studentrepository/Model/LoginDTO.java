package project.studentrepository.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String password;
    private String email;

    public LoginDTO() { // A constructor
    }
    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;


    }
}





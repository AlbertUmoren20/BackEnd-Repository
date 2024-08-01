package project.studentrepository.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String password;
    private String email;
    private Integer matricnumber;

    public LoginDTO() { // A constructor
    }
    public LoginDTO(String email, String password, Integer matricnumber) {
        this.email = email;
        this.password = password;
        this.matricnumber = matricnumber;
    }
}





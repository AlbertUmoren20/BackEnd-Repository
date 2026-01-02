package project.studentrepository.Model;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    private String password;
    private String email;
    
    @Size(max = 20, message = "Matric number must not exceed 20 characters")
    private String matricnumber;

    public LoginDTO() { // A constructor
    }
    public LoginDTO(String email, String password, String matricnumber) {
        this.email = email;
        this.password = password;
        this.matricnumber = matricnumber;
    }
}





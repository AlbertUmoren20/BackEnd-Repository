package project.studentrepository.Model;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private String fullname;
    private String password;
    private String email;
    
    @Size(max = 20, message = "Matric number must not exceed 20 characters")
    private String matricnumber;
    private Integer level;

    public StudentDto() {
    }

    @Override
    public String toString() {
        return "Student [ fullname=" + fullname + "," +
                " password=" + password + "," +
                " email=" + email + ", " +  " level=" + level + ", " +
                "matricnumber = " + matricnumber + " ]";
    }
}

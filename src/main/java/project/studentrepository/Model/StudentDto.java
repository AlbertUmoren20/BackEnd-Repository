package project.studentrepository.Model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDto {
    private String fullname;
    private String password;
    private String email;
    private Integer matricnumber;
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

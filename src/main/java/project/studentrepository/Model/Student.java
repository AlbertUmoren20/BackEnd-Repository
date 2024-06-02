package project.studentrepository.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table (name = "student")
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullname;
    private String password;
    private String email;
    private Integer matricnumber;
    private Integer level;


    @Override
    public String toString() {
        return "Student [id=" + id + ", " +
                "fullName=" + fullname + "," +
                " password=" + password + "," +
                " email=" + email + ", " +
                " level=" + level + ", " +
                "matricnumber = " + matricnumber + "]";
    }

}

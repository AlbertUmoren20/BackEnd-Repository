package project.studentrepository.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
    
    @Column(name = "matricnumber", length = 20, nullable = true)
    @Size(max = 20, message = "Matric number must not exceed 20 characters")
    private String matricnumber;
    
    @Column(nullable = true)
    private Integer level;
    
    @Column(nullable = true)
    private String role; // 'student', 'lecturer', 'admin'


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

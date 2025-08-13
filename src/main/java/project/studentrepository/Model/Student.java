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
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private Integer matricnumber;
    @Column(nullable = false)
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

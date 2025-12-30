package project.studentrepository.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "lecturer")
@Entity
public class Lecturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullname;
    private String password;
    private String email;

    @Override
    public String toString() {
        return "Lecturer [id=" + id + ", " +
                "fullName=" + fullname + "," +
                " password=" + password + "," +
                " email=" + email + "]";
    }
}


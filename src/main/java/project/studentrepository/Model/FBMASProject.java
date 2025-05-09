package project.studentrepository.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "fbmas")
public class FBMASProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String supervisor;
    private String department;
    @Column(name = "project_by") // Renamed to "project_by" to avoid reserved keyword
    private String projectBy; // Renamed from "by" to "projectBy"
    private Integer year;
}


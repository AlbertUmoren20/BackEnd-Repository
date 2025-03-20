package project.studentrepository.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "famss")
public class FAMSSProject {
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




    public FAMSSProject() {
    }

    @Override
    public String toString() {
        return "FAMSSProject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", supervisor='" + supervisor + '\'' +
                ", department='" + department + '\'' +
                ", projectBy='" + projectBy + '\'' +
                ", year=" + year +
                + '\'' +
                '}';
    }
    

}

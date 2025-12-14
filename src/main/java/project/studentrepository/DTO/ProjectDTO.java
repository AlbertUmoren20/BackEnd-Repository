package project.studentrepository.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectDTO {
    private Long id;
    private String title;
    private String description;
    private String supervisor;
    private String department;
    private String projectBy;
    private Integer year;
    private String projectType; // "FAMSS", "FBMAS", "PdfFamss", "PdfFbmas"
}

